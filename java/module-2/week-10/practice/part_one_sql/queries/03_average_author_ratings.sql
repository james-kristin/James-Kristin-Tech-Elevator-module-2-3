-- 3. List the first and last name of all authors, separated by a space, (name the column 'author') and the average star rating of their books (name the column 'average_rating').
-- Round average_rating to 2 decimal places.
-- Order by the average rating, lowest first.
-- (16 rows)

SELECT p.first_name || ' ' || p.last_name AS author, ROUND(AVG(b.star_rating), 2) AS average_rating
FROM person p
JOIN book_author ba ON p.person_id = ba.author_id
JOIN book b ON ba.book_id = b.book_id
GROUP BY author
ORDER BY average_rating;