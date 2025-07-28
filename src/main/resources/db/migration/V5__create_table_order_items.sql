CREATE TABLE order_items (
    id UUID PRIMARY KEY,
    order_id UUID NOT NULL,
    product_id UUID NOT NULL,
    quantity INT NOT NULL,
    CONSTRAINT fk_orderitem_order FOREIGN KEY (order_id) REFERENCES orders(id),
    CONSTRAINT fk_orderitem_product FOREIGN KEY (product_id) REFERENCES products(id)
);