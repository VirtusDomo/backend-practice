--DROP TABLE IF EXISTS Run;

CREATE TABLE IF NOT EXISTS Run(
    id INT NOT NULL,
    title VARCHAR(250) NOT NULL,
    started_on timestamp NOT NULL,
    completed_on timestamp NOT NULL,
    miles INT NOT NULL,
    location VARCHAR(10) NOT NULL,
    version INT,
    PRIMARY KEY (id)
);

--DROP TABLE IF EXISTS Product;

CREATE TABLE IF NOT EXISTS Product(
    id INT NOT NULL,
    name VARCHAR(250) NOT NULL,
    description VARCHAR(256) NOT NULL,
    price DECIMAL NOT NULL,
    seller_id INT NOT NULL
);

--DROP TABLE IF EXISTS Transaction;

CREATE TABLE IF NOT EXISTS Transaction(
    id INT NOT NULL,
    buyer_id INT NOT NULL,
    seller_id INT NOT NULL,
    product_id INT NOT NULL,
    amount DECIMAL NOT NULL,
    transaction_date TIMESTAMP NOT NULL
);