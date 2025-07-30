CREATE TABLE incomes (
    id UUID NOT NULL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    amount NUMERIC(10, 2) NOT NULL,
    date VARCHAR(8) NOT NULL,
    source VARCHAR(255) NOT NULL,
    user_id UUID NOT NULL,
    CONSTRAINT fk_incomes_user FOREIGN KEY (user_id) REFERENCES users(id)
);