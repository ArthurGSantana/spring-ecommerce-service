CREATE TABLE orders (
    id UUID PRIMARY KEY,
    status VARCHAR(20) NOT NULL,
    user_id UUID NOT NULL,
    shipping_address_id UUID NOT NULL,
    CONSTRAINT fk_order_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_order_address FOREIGN KEY (shipping_address_id) REFERENCES addresses(id)
);