CREATE TABLE addresses (
    id UUID PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    number VARCHAR(50) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    zip_code VARCHAR(20) NOT NULL,
    user_id UUID NOT NULL,
    CONSTRAINT fk_address_user FOREIGN KEY (user_id) REFERENCES users(id)
);