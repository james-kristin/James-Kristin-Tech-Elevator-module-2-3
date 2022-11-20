-- Drop All Tables
DROP TABLE IF EXISTS event_member;
DROP TABLE IF EXISTS event_group;
DROP TABLE IF EXISTS event;
DROP TABLE IF EXISTS group_member;
DROP TABLE IF EXISTS interest_group;
DROP TABLE IF EXISTS member;



--Table member
CREATE TABLE member(
		member_number serial NOT NULL,
		last_name varchar(200) NOT NULL,
		first_name varchar(200) NOT NULL,
		email_address varchar(200) NOT NULL,
		phone_number varchar(20) NULL,
		birth_date date NOT NULL,
		gets_reminder_emails boolean NOT NULL,
		CONSTRAINT PK_member PRIMARY KEY (member_number)
);

--Table interest_group
CREATE TABLE interest_group(
		group_number serial NOT NULL,
		group_name varchar(200) UNIQUE NOT NULL,
		CONSTRAINT PK_interest_group PRIMARY KEY (group_number)
);

--TABLE group_member
CREATE TABLE group_member(
		member_number int NOT NULL,
		group_number int NOT NULL,
		CONSTRAINT FK_group_member_member FOREIGN KEY(member_number) REFERENCES member(member_number),
		CONSTRAINT FK_group_member_interest_group FOREIGN KEY(group_number) REFERENCES interest_group(group_number)
);

--Table event
CREATE TABLE event(
		event_number serial NOT NULL,
		name varchar(200) NOT NULL,
		description text NOT NULL,
		start_date_and_time timestamp NOT NULL,
		duration int NOT NULL,
		CONSTRAINT PK_event PRIMARY KEY (event_number),
		CONSTRAINT CHK_duration CHECK (duration >= 30)
);

-- TABLE event_group
CREATE TABlE event_group(
		group_number int NOT NULL,
		event_number int NOT NULL,
		CONSTRAINT FK_event_group_interest_group FOREIGN KEY(group_number) REFERENCES interest_group(group_number),
		CONSTRAINT FK_event_group_event FOREIGN KEY(event_number) REFERENCES event(event_number)
);

-- TABLE event_member
CREATE TABLE event_member(
		member_number int NOT NULL,
		event_number int NOT NULL,
		CONSTRAINT FK_event_group_member FOREIGN KEY(member_number) REFERENCES member(member_number),
		CONSTRAINT FK_event_group_event FOREIGN KEY(event_number) REFERENCES event(event_number)
);

--Populate member (8)
INSERT INTO member (last_name, first_name, email_address, phone_number, birth_date, gets_reminder_emails) VALUES
	('Vallad', 'Hunter', 'huntercallofduty@aol.com', '123-456-7890', '2000-05-15', false),
	('Parent', 'Lexus', 'lpar9545@icloud.com', '586-456-7546', '2003-05-02', false),
	('Kristin', 'James', 'james.kristin2000@gmail.com', '456-789-4213', '2000-05-02', false),
	('Smith', 'John', 'smithyboi@hotmail.com', '157-896-4869', '1965-07-15', true),
	('Cooler', 'Guy', 'coolman@comcast.net', null, '1987-12-12', false),
	('Franco', 'Nico', 'nfranco@gmail.com', '164-789-4589', '2001-07-18', true),
	('Brobe', 'Jessica', 'jebrobe@aol.com', null, '1999-08-12', true),
	('Kudos', 'Jeffery', 'jeffkudos@yahoo.com', '456-965-1265', '1974-01-26', false);
SELECT * FROM member;

--Populate group (3)
INSERT INTO interest_group (group_name) VALUES
	('THE BOIS'),
	('Lego Fans'),
	('Bomb.com');
SELECT * FROM interest_group;

--Populate group_member (2 per)
INSERT INTO group_member (group_number, member_number) VALUES
	((SELECT group_number FROM interest_group WHERE group_name = 'THE BOIS'), (SELECT member_number FROM member WHERE first_name = 'Hunter')),
	((SELECT group_number FROM interest_group WHERE group_name = 'THE BOIS'), (SELECT member_number FROM member WHERE first_name = 'James')),
	((SELECT group_number FROM interest_group WHERE group_name = 'Bomb.com'), (SELECT member_number FROM member WHERE first_name = 'Lexus')),
	((SELECT group_number FROM interest_group WHERE group_name = 'Bomb.com'), (SELECT member_number FROM member WHERE first_name = 'Jessica')),
	((SELECT group_number FROM interest_group WHERE group_name = 'Lego Fans'), (SELECT member_number FROM member WHERE first_name = 'Jeffery')),
	((SELECT group_number FROM interest_group WHERE group_name = 'Lego Fans'), (SELECT member_number FROM member WHERE first_name = 'Nico'));
SELECT * FROM group_member gm
JOIN interest_group ig ON ig.group_number = gm.group_number
Join member m ON m.member_number = gm.member_number;

--Populate event (4 events)
INSERT INTO event (name, description, start_date_and_time, duration) VALUES
	('Fun Run For Rabies', 'Running for researching the cure for rabies.', '2022-12-15 05:00:00', 60),
	('Field Day', 'Outside event for the youth to stay active.', '2023-06-17 13:00:00', 90),
	('Movie Night', 'Showing of multiple classic movies in a marathon style.', '2023-07-22 17:20:00', 360),
	('Coding Fun', 'Learn the basic fundamentals of how to code in a fun collaborative learning format.', '2022-12-28 10:00:00', 50);
SELECT * FROM event;

--populate event_group (4)
INSERT INTO event_group (group_number, event_number) VALUES
	((SELECT group_number FROM interest_group WHERE group_name = 'Bomb.com'), (SELECT event_number FROM event WHERE name = 'Fun Run For Rabies')),
	((SELECT group_number FROM interest_group WHERE group_name = 'Lego Fans'), (SELECT event_number FROM event WHERE name = 'Field Day')),
	((SELECT group_number FROM interest_group WHERE group_name = 'THE BOIS'), (SELECT event_number FROM event WHERE name = 'Movie Night')),
	((SELECT group_number FROM interest_group WHERE group_name = 'THE BOIS'), (SELECT event_number FROM event WHERE name = 'Coding Fun'));
SELECT * FROM event_group eg
JOIN interest_group ig ON ig.group_number = eg.group_number
JOIN event e ON e.event_number = eg.event_number;

-- populate event_member (4)
INSERT INTO event_member (member_number, event_number) VALUES
	((SELECT member_number FROM member WHERE first_name = 'Lexus'), (SELECT event_number FROM event WHERE name = 'Fun Run For Rabies')),
	((SELECT member_number FROM member WHERE first_name = 'Nico'), (SELECT event_number FROM event WHERE name = 'Field Day')),
	((SELECT member_number FROM member WHERE first_name = 'Hunter'), (SELECT event_number FROM event WHERE name = 'Movie Night')),
	((SELECT member_number FROM member WHERE first_name = 'James'), (SELECT event_number FROM event WHERE name = 'Coding Fun'));
SELECT * FROM event_member em
JOIN event e ON e.event_number = em.event_number
Join member m ON m.member_number = em.member_number;


	
	
	

