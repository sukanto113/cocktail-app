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
