-- V1__init_schema.sql
-- Migration initiale pour créer les tables principales

-- Table des catégories
CREATE TABLE categories (
                            id BIGSERIAL PRIMARY KEY,
                            name VARCHAR(100) NOT NULL UNIQUE,
                            description VARCHAR(500),
                            active BOOLEAN NOT NULL DEFAULT true,
                            created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Table des produits
CREATE TABLE products (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(200) NOT NULL,
                          sku VARCHAR(50) NOT NULL UNIQUE,
                          description TEXT,
                          price DECIMAL(10, 2) NOT NULL,
                          discount_price DECIMAL(10, 2),
                          stock_quantity INTEGER NOT NULL DEFAULT 0,
                          low_stock_threshold INTEGER NOT NULL DEFAULT 10,
                          status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
                          category_id BIGINT,
                          brand VARCHAR(100),
                          supplier VARCHAR(100),
                          rating DECIMAL(3, 2),
                          review_count INTEGER DEFAULT 0,
                          created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL,
                          CONSTRAINT chk_price_positive CHECK (price > 0),
                          CONSTRAINT chk_discount_price CHECK (discount_price IS NULL OR discount_price > 0),
                          CONSTRAINT chk_stock_quantity CHECK (stock_quantity >= 0),
                          CONSTRAINT chk_rating CHECK (rating IS NULL OR (rating >= 0 AND rating <= 5))
);

-- Table des images de produits
CREATE TABLE product_images (
                                product_id BIGINT NOT NULL,
                                image_url VARCHAR(500) NOT NULL,
                                CONSTRAINT fk_product_images FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- Index pour améliorer les performances
CREATE INDEX idx_products_category_id ON products(category_id);
CREATE INDEX idx_products_status ON products(status);
CREATE INDEX idx_products_sku ON products(sku);
CREATE INDEX idx_products_name ON products(name);
CREATE INDEX idx_products_price ON products(price);
CREATE INDEX idx_products_created_at ON products(created_at);
CREATE INDEX idx_categories_name ON categories(name);
CREATE INDEX idx_categories_active ON categories(active);

-- Trigger pour mettre à jour automatiquement updated_at
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_products_updated_at
    BEFORE UPDATE ON products
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_categories_updated_at
    BEFORE UPDATE ON categories
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();