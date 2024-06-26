CREATE TABLE user_info
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name  VARCHAR(256) NOT NULL,
    email    VARCHAR(256) UNIQUE NOT NULL,
    password VARCHAR(256) NOT NULL,
    active BOOLEAN NOT NULL,
    picture VARCHAR(255),
    phone VARCHAR(20),
    street VARCHAR(255),
    city VARCHAR(255),
    country VARCHAR(255)
);

CREATE TABLE category
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(256) NOT NULL,
    picture VARCHAR(256) NOT NULL
);

CREATE TABLE product
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    category_id INT NOT NULL,
    name VARCHAR(256) NOT NULL,
    picture VARCHAR(256) NOT NULL,
    ingredients TEXT NOT NULL,
    method TEXT NOT NULL
);

CREATE TABLE favorite
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    CONSTRAINT uc_user_product UNIQUE(user_id, product_id)
);