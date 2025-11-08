-- V2__insert_sample_data.sql
-- Insertion de données de test

-- Insertion des catégories
INSERT INTO categories (name, description, active) VALUES
                                                       ('Électronique', 'Appareils et accessoires électroniques', true),
                                                       ('Vêtements', 'Vêtements pour hommes, femmes et enfants', true),
                                                       ('Maison & Jardin', 'Produits pour la maison et le jardin', true),
                                                       ('Sports & Loisirs', 'Équipements sportifs et loisirs', true),
                                                       ('Livres', 'Livres et magazines', true),
                                                       ('Alimentation', 'Produits alimentaires et boissons', true);

-- Insertion des produits
INSERT INTO products (name, sku, description, price, discount_price, stock_quantity, low_stock_threshold, status, category_id, brand, rating, review_count) VALUES
-- Électronique
('Smartphone Samsung Galaxy S23', 'ELEC-001', 'Smartphone haut de gamme avec écran AMOLED 6.1 pouces', 899.99, 799.99, 50, 10, 'ACTIVE', 1, 'Samsung', 4.5, 128),
('MacBook Pro 14 pouces', 'ELEC-002', 'Ordinateur portable professionnel avec puce M3', 2199.00, NULL, 25, 5, 'ACTIVE', 1, 'Apple', 4.8, 95),
('Casque Sony WH-1000XM5', 'ELEC-003', 'Casque sans fil à réduction de bruit', 399.99, 349.99, 100, 20, 'ACTIVE', 1, 'Sony', 4.7, 210),
('Tablette iPad Air', 'ELEC-004', 'Tablette 10.9 pouces avec puce M1', 699.00, NULL, 8, 10, 'ACTIVE', 1, 'Apple', 4.6, 156),

-- Vêtements
('T-shirt Coton Bio Homme', 'VET-001', 'T-shirt 100% coton biologique, plusieurs couleurs', 29.99, 24.99, 200, 30, 'ACTIVE', 2, 'EcoWear', 4.3, 87),
('Jean Slim Femme', 'VET-002', 'Jean stretch confortable, coupe slim', 79.99, NULL, 150, 25, 'ACTIVE', 2, 'Levi''s', 4.4, 142),
('Veste Hiver Homme', 'VET-003', 'Veste imperméable avec doublure chaude', 149.99, 119.99, 45, 10, 'ACTIVE', 2, 'Columbia', 4.5, 93),

-- Maison & Jardin
('Aspirateur Robot', 'MAIS-001', 'Robot aspirateur avec navigation intelligente', 299.99, 249.99, 35, 10, 'ACTIVE', 3, 'Xiaomi', 4.2, 178),
('Cafetière Nespresso', 'MAIS-002', 'Machine à café avec système de capsules', 189.00, NULL, 60, 15, 'ACTIVE', 3, 'Nespresso', 4.6, 234),
('Set de Casseroles', 'MAIS-003', 'Set de 5 casseroles antiadhésives', 129.99, 99.99, 80, 20, 'ACTIVE', 3, 'Tefal', 4.3, 156),

-- Sports & Loisirs
('Vélo VTT 27.5 pouces', 'SPORT-001', 'VTT tout-terrain avec suspension', 599.00, 549.00, 15, 5, 'ACTIVE', 4, 'Trek', 4.7, 67),
('Tapis de Yoga', 'SPORT-002', 'Tapis antidérapant 6mm d''épaisseur', 39.99, NULL, 120, 25, 'ACTIVE', 4, 'Manduka', 4.5, 189),
('Ballon de Football', 'SPORT-003', 'Ballon officiel taille 5', 24.99, 19.99, 200, 40, 'ACTIVE', 4, 'Nike', 4.4, 312),

-- Livres
('Le Petit Prince', 'LIV-001', 'Roman classique d''Antoine de Saint-Exupéry', 12.99, NULL, 300, 50, 'ACTIVE', 5, 'Gallimard', 4.9, 1250),
('1984 de George Orwell', 'LIV-002', 'Roman dystopique culte', 14.99, NULL, 250, 50, 'ACTIVE', 5, 'Penguin', 4.8, 987),

-- Alimentation
('Café Arabica Premium', 'ALIM-001', 'Café en grains 100% Arabica 1kg', 24.99, 21.99, 100, 20, 'ACTIVE', 6, 'Lavazza', 4.6, 412),
('Huile d''Olive Extra Vierge', 'ALIM-002', 'Huile d''olive de première pression 1L', 18.99, NULL, 150, 30, 'ACTIVE', 6, 'Filippo Berio', 4.7, 289);

-- Insertion des images de produits (exemples)
INSERT INTO product_images (product_id, image_url) VALUES
                                                       (1, 'https://images.example.com/products/samsung-s23-1.jpg'),
                                                       (1, 'https://images.example.com/products/samsung-s23-2.jpg'),
                                                       (2, 'https://images.example.com/products/macbook-pro-1.jpg'),
                                                       (3, 'https://images.example.com/products/sony-headphones-1.jpg'),
                                                       (4, 'https://images.example.com/products/ipad-air-1.jpg'),
                                                       (5, 'https://images.example.com/products/tshirt-1.jpg'),
                                                       (6, 'https://images.example.com/products/jean-1.jpg'),
                                                       (7, 'https://images.example.com/products/jacket-1.jpg'),
                                                       (8, 'https://images.example.com/products/robot-vacuum-1.jpg'),
                                                       (9, 'https://images.example.com/products/nespresso-1.jpg'),
                                                       (10, 'https://images.example.com/products/cookware-1.jpg'),
                                                       (11, 'https://images.example.com/products/mountain-bike-1.jpg'),
                                                       (12, 'https://images.example.com/products/yoga-mat-1.jpg'),
                                                       (13, 'https://images.example.com/products/football-1.jpg'),
                                                       (14, 'https://images.example.com/products/petit-prince-1.jpg'),
                                                       (15, 'https://images.example.com/products/1984-1.jpg'),
                                                       (16, 'https://images.example.com/products/coffee-1.jpg'),
                                                       (17, 'https://images.example.com/products/olive-oil-1.jpg');