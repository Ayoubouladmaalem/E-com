from sentence_transformers import SentenceTransformer
import faiss
import numpy as np
from typing import List, Dict, Optional
import pickle
import os
import logging

logger = logging.getLogger(__name__)


class VectorStore:
    """
    Vector store for product embeddings using FAISS
    """
    
    def __init__(self, model_name: str = None):
        """
        Initialize the vector store
        
        Args:
            model_name: Name of the sentence transformer model to use
        """
        # Get model name from env or use default
        if model_name is None:
            model_name = os.getenv("EMBEDDING_MODEL", "all-MiniLM-L6-v2")
        
        logger.info(f"Loading embedding model: {model_name}")
        self.model = SentenceTransformer(model_name)
        self.dimension = 384  # Dimension for all-MiniLM-L6-v2
        self.data_dir = os.getenv("DATA_DIR", "./data")
        
        # Initialize FAISS index
        self.index = faiss.IndexFlatL2(self.dimension)
        self.products = []  # Store product metadata
        
        # Load existing index if available
        self._load_index()
    
    def _create_product_text(self, product: Dict) -> str:
        """
        Create searchable text from product data
        """
        text_parts = [
            f"Product: {product.get('name', '')}",
            f"Description: {product.get('description', '')}",
            f"Category: {product.get('category', 'N/A')}",
            f"Price: ${product.get('price', 0)}"
        ]
        
        # Add category description if available
        if product.get('category_description'):
            text_parts.append(f"Category Info: {product.get('category_description')}")
        
        return " | ".join(text_parts)
    
    def index_products(self, products: List[Dict]) -> int:
        """
        Index products into the vector store
        
        Args:
            products: List of product dictionaries
        
        Returns:
            Number of products indexed
        """
        if not products:
            return 0
        
        logger.info(f"Creating embeddings for {len(products)} products...")
        # Create embeddings
        texts = [self._create_product_text(p) for p in products]
        embeddings = self.model.encode(texts, show_progress_bar=True)
        
        # Add to FAISS index
        self.index.add(np.array(embeddings).astype('float32'))
        
        # Store product metadata
        self.products.extend(products)
        
        # Save index
        self._save_index()
        
        logger.info(f"Successfully indexed {len(products)} products")
        return len(products)
    
    def search(self, query: str, top_k: int = 5) -> List[Dict]:
        """
        Search for products using semantic similarity
        
        Args:
            query: Search query
            top_k: Number of results to return
        
        Returns:
            List of products with similarity scores
        """
        if self.index.ntotal == 0:
            return []
        
        # Create query embedding
        query_embedding = self.model.encode([query])
        
        # Search in FAISS
        distances, indices = self.index.search(
            np.array(query_embedding).astype('float32'), 
            min(top_k, self.index.ntotal)
        )
        
        # Prepare results
        results = []
        for i, (dist, idx) in enumerate(zip(distances[0], indices[0])):
            if idx < len(self.products):
                product = self.products[idx].copy()
                # Convert L2 distance to similarity score (0-1, higher is better)
                product["score"] = float(1 / (1 + dist))
                results.append(product)
        
        return results
    
    def clear(self):
        """
        Clear the vector store
        """
        self.index = faiss.IndexFlatL2(self.dimension)
        self.products = []
        self._save_index()
    
    def get_stats(self) -> Dict:
        """
        Get statistics about the vector store
        """
        return {
            "total_products": self.index.ntotal,
            "dimension": self.dimension,
            "model": os.getenv("EMBEDDING_MODEL", "all-MiniLM-L6-v2"),
            "data_directory": self.data_dir
        }
    
    def _save_index(self):
        """
        Save FAISS index and product metadata to disk
        """
        os.makedirs(self.data_dir, exist_ok=True)
        
        index_path = os.path.join(self.data_dir, "faiss_index.bin")
        products_path = os.path.join(self.data_dir, "products.pkl")
        
        # Save FAISS index
        faiss.write_index(self.index, index_path)
        
        # Save product metadata
        with open(products_path, "wb") as f:
            pickle.dump(self.products, f)
        
        logger.debug(f"Saved index to {index_path}")
    
    def _load_index(self):
        """
        Load FAISS index and product metadata from disk
        """
        try:
            index_path = os.path.join(self.data_dir, "faiss_index.bin")
            products_path = os.path.join(self.data_dir, "products.pkl")
            
            if os.path.exists(index_path):
                self.index = faiss.read_index(index_path)
                logger.info(f"Loaded FAISS index with {self.index.ntotal} products")
            
            if os.path.exists(products_path):
                with open(products_path, "rb") as f:
                    self.products = pickle.load(f)
                logger.info(f"Loaded {len(self.products)} product metadata entries")
        except Exception as e:
            logger.error(f"Error loading index: {e}")
            self.index = faiss.IndexFlatL2(self.dimension)
            self.products = []
