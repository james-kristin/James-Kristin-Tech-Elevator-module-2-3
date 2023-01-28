BEGIN TRANSACTION;

DROP TABLE IF EXISTS book_author, book, person, publisher CASCADE;

CREATE TABLE publisher(
	publisher_id serial,
	publisher_name varchar(40) NOT NULL,
	CONSTRAINT PK_publisher PRIMARY KEY(publisher_id)
);

CREATE TABLE person(
	person_id serial,
	first_name varchar(40) NOT NULL,
	last_name varchar(40) NOT NULL,
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

-- 4 publishers
INSERT INTO publisher (publisher_name) VALUES ('T&E Publishing'), ('Stapled Books'), ('Penguin Predictable House'), ('Garfield & Schuster');

-- 19 people for authors and forewords
INSERT INTO person (first_name, last_name) VALUES
('Gerti', 'Speller'), ('Ranice', 'Hissett'), ('Moishe', 'Reiling'), ('Harley', 'Diglin'), ('Cherri', 'Vallens'),
('Celestine', 'Zecchini'), ('Gusta', 'Parramore'), ('Hyacinth', 'Kenrick'), ('Denna', 'Cettell'), ('Derek', 'Sizzey'),
('Clemmie', 'Scholling'), ('Sylvester', 'Taylor'), ('Maxie', 'Docwra'), ('Reynold', 'Chang'), ('Welsh', 'Hatwells'),
('Emylee', 'Kimmons'), ('Dyana', 'Kopisch'), ('Niki', 'Clardge'), ('Roseanne', 'Ratt');

-- publisher 1 books, 1-9
INSERT INTO book (book_title, star_rating, out_of_print, foreword_by, publisher_id, published_date) VALUES
('Serpent Of Power', 3.7, false, NULL, 1, '2019-04-19'),
('Horses Of The Mountain', 0.4, false, NULL, 1, '2018-09-11'),
('Heirs Without Courage', 4.4, false, 1, 1, '2007-08-05'),
('Bandits And Heroes', 2.1, true, 2, 1, '2015-12-01'),
('Enemies And Spies', 4.8, true, 2, 1, '2009-08-24'),
('Argument Of Sorrow', 2.1, false, NULL, 1, '2018-10-23'),
('World Without Courage', 4.7, true, 4, 1, '2019-01-24'),
('Trust The Dungeons', 1.3, true, 5, 1, '2015-01-12'),
('Faith Of My Dreams', 0.9, true, 5, 1, '2006-12-16');

-- publisher 2 books, 10-17
INSERT INTO book (book_title, star_rating, out_of_print, foreword_by, publisher_id, published_date) VALUES
('Creator Of Heaven', 3.9, false, 6, 2, '2002-04-24'),
('Man Without Sin', 2.3, true, NULL, 2, '2009-01-31'),
('Soldiers Without A Head', 4.0, true, 6, 2, '2004-11-07'),
('Lords And Officers', 4.3, false, NULL, 2, '2007-07-30'),
('Men And Rebels', 4.6, true, 7, 2, '2008-02-24'),
('Creation Of Freedom', 4.7, false, NULL, 2, '2022-01-31'),
('Forsaken By The Traitors', 4.2, true, 6, 2, '2020-11-04'),
('Dead At The Depths', 2.5, false, 7, 2, '2011-08-23');

-- publisher 3 books, 18-25
INSERT INTO book (book_title, star_rating, out_of_print, foreword_by, publisher_id, published_date) VALUES
('Owl In Space', 3.8, true, NULL, 3, '2008-09-13'),
('Toad Of The Sun', 2.5, false, NULL, 3, '2013-06-24'),
('Wizards Of Life', 3.3, true, NULL, 3, '2004-12-09'),
('Kids And Goats', 3.0, false, 13, 3, '2011-07-03'),
('Mice And Kings', 1.8, false, 14, 3, '2010-12-18'),
('Home Of My Imagination', 3.6, false, NULL, 3, '2022-01-25'),
('Clumsy Cat', 3.9, false, NULL, 3, '2013-06-30'),
('Present From The Neighbor', 1.0, true, NULL, 3, '2018-09-08');

-- publisher 4 books, 26-33
INSERT INTO book (book_title, star_rating, out_of_print, foreword_by, publisher_id, published_date) VALUES
('King Of The World', 3.6, true, 17, 4, '2015-10-13'),
('Swindler Of Sorrow', 3.1, true, 17, 4, '2009-03-18'),
('Gnomes Of The South', 4.6, false, NULL, 4, '2013-07-13'),
('Gangsters Of Yesterday', 1.6, true, 13, 4, '2010-05-20'),
('Priests And Foes', 4.6, false, NULL, 4, '2004-01-31'),
('Spies And Necromancers', 1.8, true, 15, 4, '2006-08-10'),
('Moon Of Dusk', 0.5, true, 14, 4, '2001-10-14'),
('Renewal Of The Mountain', 4.5, false, NULL, 4, '2022-01-29');

INSERT INTO book_author (book_id, author_id) VALUES
(1, 1), (1, 2),
(2, 2),
(3, 2), (3, 3),
(4, 3), (4, 4), (4, 5),
(5, 5),
(6, 6), (6, 2),
(7, 2),
(8, 3),
(9, 3),
(10, 5),
(11, 5), (11, 6),
(12, 6),
(13, 8), (13, 12),
(14, 9),
(15, 10), (15, 11), (15, 12),
(16, 12),
(17, 9),
(18, 13),
(19, 13), (19, 14),
(20, 14),
(21, 16),
(22, 14), (22, 16),
(23, 17),
(24, 18),
(25, 18),
(26, 2), (26, 3),
(27, 1),
(28, 1),
(29, 5),
(30, 4), (30, 5),
(31, 5),
(32, 6),
(33, 6);

COMMIT;