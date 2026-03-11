drop database if exists progetto_tsw;
create database progetto_tsw;
use progetto_tsw;

CREATE TABLE User (
    ID_User INT AUTO_INCREMENT PRIMARY KEY,
    FirstName VARCHAR(255) NOT NULL,
    LastName VARCHAR(255) NOT NULL,
    Email VARCHAR(255) NOT NULL UNIQUE,
    Password VARCHAR(255) NOT NULL,
    Admin BOOLEAN NOT NULL DEFAULT 0,
    Provincia VARCHAR(255) NOT NULL,
    Via VARCHAR(255) NOT NULL,
    Civico VARCHAR(10) NOT NULL,
    CAP CHAR(5) NOT NULL,
    Citta VARCHAR(255) NOT NULL,
    CHECK (Email REGEXP '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$')
);

CREATE TABLE `Order`(
    ID_Order INT AUTO_INCREMENT PRIMARY KEY,
    ID_User INT,
    DATE DATE,
    State VARCHAR(255),
    FOREIGN KEY(ID_User) REFERENCES User(ID_User)
);

CREATE TABLE Category (
    ID_Category INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(255)
);

CREATE TABLE Product (
    ID_Product INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Quantity INT NOT NULL,
    Image_url TEXT DEFAULT ("https://via.placeholder.com/300x225"),
    Price DECIMAL(10, 2) NOT NULL,
    Description TEXT
);

CREATE TABLE CategoryProduct (
    ID_Category INT,
    ID_Product INT,
    PRIMARY KEY(ID_Category, ID_Product),
    FOREIGN KEY(ID_Category) REFERENCES Category(ID_Category),
    FOREIGN KEY(ID_Product) REFERENCES Product(ID_Product)
);

CREATE TABLE Feedback (
    ID_Feedback INT AUTO_INCREMENT PRIMARY KEY,
    Title VARCHAR(255) NOT NULL,
    Description TEXT NOT NULL,
    Score INT NOT NULL,
    ID_User INT,
    ID_Product INT,
    FOREIGN KEY (ID_User) REFERENCES User(ID_User),
    FOREIGN KEY (ID_Product) REFERENCES Product(ID_Product)
);

CREATE TABLE ProductOrder (
    ID_ProductOrder INT AUTO_INCREMENT PRIMARY KEY,
    Quantity INT NOT NULL,
    Price DECIMAL(10, 2) NOT NULL,
    ID_Order INT,
    ID_Product INT,
    FOREIGN KEY (ID_Order) REFERENCES `Order`(ID_Order),
    FOREIGN KEY (ID_Product) REFERENCES Product(ID_Product)
);
