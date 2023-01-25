-- 1. List the title, the date published, and the star_rating of all books with a star_rating of at least 3.
-- Order the results by rating, with the highest first.
-- (20 rows)

SELECT book_title, published_date, star_rating
FROM book
WHERE star_rating >= 3
ORDER BY star_rating;
