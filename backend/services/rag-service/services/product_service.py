from sqlalchemy import create_engine, text
from sqlalchemy.orm import sessionmaker
from typing import List, Optional, Dict
import os
import httpx


class ProductService:
    """
    Service to fetch products from PostgreSQL or Product Service API
    """
    
    def __init__(self):
        # Try to connect to Product Service API first, fallback to direct DB
        self.product_service_url = os.getenv("PRODUCT_SERVICE_URL")
        self.use_api = self.product_service_url is not None
        
        if not self.use_api:
            # Direct database connection
            database_url = os.getenv(
                "DATABASE_URL",
                "postgresql://postgres:password@localhost:5432/products"
            )
            self.engine = create_engine(database_url)
            self.SessionLocal = sessionmaker(bind=self.engine)
    
    async def get_products_from_api(self, product_ids: Optional[List[str]] = None) -> List[Dict]:
        """
        Fetch products from Product Service REST API
        """
        try:
            async with httpx.AsyncClient() as client:
                if product_ids:
                    # Fetch specific products
                    products = []
                    for product_id in product_ids:
                        response = await client.get(f"{self.product_service_url}/{product_id}")
                        if response.status_code == 200:
                            products.append(response.json())
                    return self._normalize_products(products)
                else:
                    # Fetch all products
                    response = await client.get(self.product_service_url)
                    if response.status_code == 200:
                        return self._normalize_products(response.json())
            return []
        except Exception as e:
            print(f"Error fetching products from API: {e}")
            return []
    
    def get_products_from_db(self, product_ids: Optional[List[str]] = None) -> List[Dict]:
        """
        Fetch products directly from PostgreSQL database
        """
        try:
            session = self.SessionLocal()
            
            if product_ids:
                # Convert to proper SQL with parameter binding
                placeholders = ','.join([f':id{i}' for i in range(len(product_ids))])
                query = text(f"""
                    SELECT p.id, p.name, p.description, p.price, p.available_quantity, 
                           c.name as category_name, c.description as category_desc
                    FROM product p
                    LEFT JOIN category c ON p.category_id = c.id
                    WHERE p.id IN ({placeholders})
                """)
                params = {f'id{i}': int(pid) for i, pid in enumerate(product_ids)}
                result = session.execute(query, params)
            else:
                query = text("""
                    SELECT p.id, p.name, p.description, p.price, p.available_quantity,
                           c.name as category_name, c.description as category_desc
                    FROM product p
                    LEFT JOIN category c ON p.category_id = c.id
                """)
                result = session.execute(query)
            
            products = []
            for row in result:
                products.append({
                    "id": str(row[0]),
                    "name": row[1] or "",
                    "description": row[2] or "",
                    "price": float(row[3]) if row[3] else 0.0,
                    "stock": int(row[4]) if row[4] else 0,
                    "category": row[5],  # category name
                    "category_description": row[6]  # category description
                })
            
            session.close()
            return products
        except Exception as e:
            print(f"Error fetching products from database: {e}")
            return []
    
    def get_products(self, product_ids: Optional[List[str]] = None) -> List[Dict]:
        """
        Fetch products (uses API if available, otherwise direct DB)
        
        Args:
            product_ids: Optional list of product IDs to fetch. If None, fetch all products.
        
        Returns:
            List of product dictionaries
        """
        if self.use_api:
            # Note: This is a sync wrapper for async API calls
            import asyncio
            try:
                loop = asyncio.get_event_loop()
            except RuntimeError:
                loop = asyncio.new_event_loop()
                asyncio.set_event_loop(loop)
            return loop.run_until_complete(self.get_products_from_api(product_ids))
        else:
            return self.get_products_from_db(product_ids)
    
    def get_product_by_id(self, product_id: str) -> Optional[Dict]:
        """
        Fetch a single product by ID
        """
        products = self.get_products([product_id])
        return products[0] if products else None
    
    def _normalize_products(self, products: List[Dict]) -> List[Dict]:
        """
        Normalize product data from API response
        """
        normalized = []
        for product in products:
            # Handle both direct product data and nested category
            category_name = None
            if isinstance(product.get("category"), dict):
                category_name = product["category"].get("name")
            elif isinstance(product.get("category"), str):
                category_name = product["category"]
            
            normalized.append({
                "id": str(product.get("id", "")),
                "name": product.get("name", ""),
                "description": product.get("description", ""),
                "price": float(product.get("price", 0.0)),
                "stock": product.get("availableQuantity", 0),
                "category": category_name,
                "category_description": product.get("category", {}).get("description") if isinstance(product.get("category"), dict) else None
            })
        return normalized
    
    def __del__(self):
        """Close database connection"""
        if hasattr(self, 'engine'):
            self.engine.dispose()
