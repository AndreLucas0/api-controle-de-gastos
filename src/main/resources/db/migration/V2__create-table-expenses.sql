CREATE TABLE expenses (
    id UUID NOT NULL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    amount NUMERIC(10, 2) NOT NULL,
    date VARCHAR(8) NOT NULL,
    payment_method VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    user_id UUID NOT NULL,
    CONSTRAINT fk_expenses_user FOREIGN KEY (user_id) REFERENCES users(id)
);