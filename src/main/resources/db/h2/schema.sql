CREATE TABLE IF NOT EXISTS TODOS (
    id bigint NOT NULL AUTO_INCREMENT,
    title varchar(100) NOT NULL,
    todo_order int NOT NULL,
    completed boolean NOT NULL,
    PRIMARY KEY (id)
);