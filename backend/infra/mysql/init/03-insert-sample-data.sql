-- User Service 샘플 데이터
USE `user_service_db`;

INSERT INTO users (email, password, name) VALUES
    ('admin@example.com', 'admin123', '관리자'),
    ('kim@example.com', 'password', '김철수'),
    ('lee@example.com', 'password', '이영희'),
    ('park@example.com', 'password', '박민수'),
    ('choi@example.com', 'password', '최지은');

-- Product Service 샘플 데이터
USE `product_service_db`;

INSERT INTO products (name, description, price, stock_quantity, category, image_url) VALUES
    ('MacBook Air M2', 'Apple MacBook Air with M2 chip, 13-inch display', 1290000.00, 15, 'Electronics', 'https://example.com/images/macbook-air.jpg'),
    ('iPhone 15 Pro', 'Latest iPhone with A17 Pro chip', 1550000.00, 25, 'Electronics', 'https://example.com/images/iphone15.jpg'),
    ('Nike Air Max', 'Comfortable running shoes for daily wear', 120000.00, 50, 'Fashion', 'https://example.com/images/nike-airmax.jpg'),
    ('Samsung 4K Monitor', '32-inch 4K UHD monitor with USB-C', 450000.00, 8, 'Electronics', 'https://example.com/images/samsung-monitor.jpg'),
    ('Starbucks Tumbler', 'Reusable coffee tumbler 473ml', 35000.00, 100, 'Lifestyle', 'https://example.com/images/starbucks-tumbler.jpg'),
    ('Wireless Keyboard', 'Bluetooth mechanical keyboard', 89000.00, 30, 'Electronics', 'https://example.com/images/keyboard.jpg'),
    ('Cotton T-Shirt', 'Basic cotton t-shirt, multiple colors', 25000.00, 200, 'Fashion', 'https://example.com/images/tshirt.jpg'),
    ('Coffee Beans 1kg', 'Premium Arabica coffee beans', 45000.00, 60, 'Food', 'https://example.com/images/coffee-beans.jpg'),
    ('Yoga Mat', 'Non-slip yoga mat for exercise', 65000.00, 40, 'Sports', 'https://example.com/images/yoga-mat.jpg'),
    ('Bluetooth Speaker', 'Portable wireless speaker with bass', 95000.00, 35, 'Electronics', 'https://example.com/images/speaker.jpg');
