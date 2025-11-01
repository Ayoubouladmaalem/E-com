from fastapi import FastAPI, HTTPException
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel
from typing import List, Optional
import os
import logging
from dotenv import load_dotenv

from services.vector_store import VectorStore
from services.product_service import ProductService

# Load environment variables
load_dotenv()

# Configure logging
log_level = os.getenv("LOG_LEVEL", "INFO")
logging.basicConfig(
    level=getattr(logging, log_level),
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s'
)
logger = logging.getLogger(__name__)

# Service configuration
SERVICE_NAME = os.getenv("SERVICE_NAME", "rag-service")
SERVICE_PORT = int(os.getenv("SERVICE_PORT", 8000))
SERVICE_HOST = os.getenv("SERVICE_HOST", "0.0.0.0")
ENVIRONMENT = os.getenv("ENVIRONMENT", "development")

# CORS configuration
cors_origins = os.getenv("CORS_ORIGINS", "*")
allowed_origins = cors_origins.split(",") if cors_origins != "*" else ["*"]

app = FastAPI(
    title="Product RAG Service",
    description="RAG system for product context retrieval using semantic search",
    version="1.0.0",
    docs_url="/docs" if ENVIRONMENT == "development" else None,
    redoc_url="/redoc" if ENVIRONMENT == "development" else None
)

# CORS middleware
app.add_middleware(
    CORSMiddleware,
    allow_origins=allowed_origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Initialize services
logger.info(f"Initializing {SERVICE_NAME} in {ENVIRONMENT} mode...")
vector_store = VectorStore()
product_service = ProductService()
logger.info("Services initialized successfully")


class QueryRequest(BaseModel):
    query: str
    top_k: Optional[int] = None  # Will use DEFAULT_TOP_K from env if not provided


class ProductContext(BaseModel):
    id: str
    name: str
    description: str
    price: float
    category: Optional[str] = None
    score: float


class QueryResponse(BaseModel):
    query: str
    context: List[ProductContext]
    total_results: int


class IndexRequest(BaseModel):
    product_ids: Optional[List[str]] = None


@app.get("/")
async def root():
    return {
        "service": SERVICE_NAME,
        "status": "running",
        "version": "1.0.0",
        "environment": ENVIRONMENT
    }


@app.get("/health")
async def health_check():
    return {
        "status": "healthy",
        "service": SERVICE_NAME,
        "environment": ENVIRONMENT
    }


@app.post("/query", response_model=QueryResponse)
async def query_products(request: QueryRequest):
    """
    Query products using semantic search
    """
    try:
        # Use DEFAULT_TOP_K from env if not provided
        top_k = request.top_k if request.top_k is not None else int(os.getenv("DEFAULT_TOP_K", 5))
        
        logger.info(f"Querying products: '{request.query}' (top_k={top_k})")
        results = vector_store.search(request.query, top_k=top_k)
        
        context = []
        for result in results:
            context.append(ProductContext(
                id=result["id"],
                name=result["name"],
                description=result["description"],
                price=result["price"],
                category=result.get("category"),
                score=result["score"]
            ))
        
        logger.info(f"Found {len(context)} products for query: '{request.query}'")
        return QueryResponse(
            query=request.query,
            context=context,
            total_results=len(context)
        )
    except Exception as e:
        logger.error(f"Error querying products: {e}")
        raise HTTPException(status_code=500, detail=str(e))


@app.post("/index")
async def index_products(request: IndexRequest = None):
    """
    Index products into the vector store
    """
    try:
        logger.info("Starting product indexing...")
        # Fetch products from MongoDB
        products = product_service.get_products(request.product_ids if request else None)
        
        if not products:
            logger.warning("No products to index")
            return {"message": "No products to index", "count": 0}
        
        # Index products
        count = vector_store.index_products(products)
        logger.info(f"Successfully indexed {count} products")
        
        return {
            "message": "Products indexed successfully",
            "count": count
        }
    except Exception as e:
        logger.error(f"Error indexing products: {e}")
        raise HTTPException(status_code=500, detail=str(e))


@app.delete("/index")
async def clear_index():
    """
    Clear the vector store index
    """
    try:
        logger.warning("Clearing vector store index...")
        vector_store.clear()
        logger.info("Vector store cleared successfully")
        return {"message": "Index cleared successfully"}
    except Exception as e:
        logger.error(f"Error clearing index: {e}")
        raise HTTPException(status_code=500, detail=str(e))


@app.get("/stats")
async def get_stats():
    """
    Get statistics about the indexed products
    """
    try:
        stats = vector_store.get_stats()
        stats["service"] = SERVICE_NAME
        stats["environment"] = ENVIRONMENT
        return stats
    except Exception as e:
        logger.error(f"Error getting stats: {e}")
        raise HTTPException(status_code=500, detail=str(e))


if __name__ == "__main__":
    import uvicorn
    logger.info(f"Starting {SERVICE_NAME} on {SERVICE_HOST}:{SERVICE_PORT}")
    uvicorn.run(app, host=SERVICE_HOST, port=SERVICE_PORT)
