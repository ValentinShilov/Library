INSERT INTO authors (name, description) VALUES ('Автор 1', 'Описание автора 1');
INSERT INTO authors (name, description) VALUES ('Автор 2', 'Описание автора 2');
INSERT INTO authors (name, description) VALUES ('Автор 3', 'Описание автора 3');

INSERT INTO books (name, author_id) VALUES ('Книга 1', 1);
INSERT INTO books (name, author_id) VALUES ('Книга 2', 1);
INSERT INTO books (name, author_id) VALUES ('Книга 3', 2);
INSERT INTO books (name, author_id) VALUES ('Книга 4', 3);
INSERT INTO books (name, author_id) VALUES ('Книга 5', 3);

INSERT INTO users (username, email) VALUES ('user1', 'user1@example.com');
INSERT INTO users (username, email) VALUES ('user2', 'user2@example.com');
INSERT INTO users (username, email) VALUES ('user3', 'user3@example.com');

INSERT INTO comments (text, user_id, book_id) VALUES ('Комментарий 1 к книге 1', 1, 1);
INSERT INTO comments (text, user_id, book_id) VALUES ('Комментарий 2 к книге 1', 2, 1);
INSERT INTO comments (text, user_id, book_id) VALUES ('Комментарий к книге 2', 3, 2);
INSERT INTO comments (text, user_id, book_id) VALUES ('Комментарий 1 к книге 3', 1, 3);
INSERT INTO comments (text, user_id, book_id) VALUES ('Комментарий 1 к книге 4', 2, 4);