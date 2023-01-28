BEGIN TRANSACTION;

DROP TABLE IF EXISTS book_author, book, person, publisher CASCADE;

CREATE TABLE publisher(
	publisher_id serial,
	publisher_name varchar(40) NOT NULL,
	CONSTRAINT PK_publisher PRIMARY KEY(publisher_id)
);

CREATE TABLE person(
	person_id serial,
	person_name varchar(40) NOT NULL,
	CONSTRAINT PK_person PRIMARY KEY(person_id)
);

CREATE TABLE book(
	book_id serial,
	book_title varchar(200) NOT NULL,
	star_rating decimal(2, 1) NOT NULL,
	out_of_print boolean NOT NULL CONSTRAINT DF_out_of_print DEFAULT (false),
	foreword_by int,
	publisher_id int NOT NULL,
	published_date date NOT NULL,
	CONSTRAINT PK_book PRIMARY KEY(book_id),
    CONSTRAINT FK_book_publisher_id FOREIGN KEY(publisher_id) REFERENCES publisher(publisher_id),
	CONSTRAINT FK_book_foreword_by FOREIGN KEY(foreword_by) REFERENCES person(person_id),
	CONSTRAINT CK_star_rating CHECK (star_rating >= 0.0 AND star_rating <= 5.0)
);

CREATE TABLE book_author(
	book_id int NOT NULL,
	author_id int NOT NULL,
	CONSTRAINT PK_book_author PRIMARY KEY(book_id, author_id),
    CONSTRAINT FK_book_author_book_id FOREIGN KEY(book_id) REFERENCES book(book_id),
	CONSTRAINT FK_book_author_author_id FOREIGN KEY(author_id) REFERENCES person(person_id)
);

INSERT INTO publisher (publisher_name) VALUES ('PUBLISHER 1'), ('PUBLISHER 2');

INSERT INTO person (person_name) VALUES ('PERSON 1'), ('PERSON 2'), ('PERSON 3'), ('PERSON 4');

INSERT INTO book (book_title, star_rating, out_of_print, foreword_by, publisher_id, published_date) VALUES
('BOOK 1', 5.0, false, 2, 1, '2022-01-01'),
('BOOK 2', 4.9, false, 1, 2, '2022-02-02'),
('BOOK 3', 2.5, true, NULL, 1,'2022-03-03'),
('BOOK 4', 0.0, true, NULL, 2, '2022-04-04');

INSERT INTO book_author (book_id, author_id) VALUES
(1, 1),
(2, 2),
(3, 3), (3, 4),
(4, 3), (4, 4);

COMMIT;
