-- 3. List the publisher name and the highest star rating for each publisher (name the column 'highest_rating'). 
-- Order the results by rating, highest first.
-- (4 rows, starting with T&E Publishing)

SELECT p.publisher_name, MAX(b.star_rating) AS highest_rating
FROM publisher p
JOIN book b on p.publisher_id = b.publisher_id
GROUP BY p.publisher_name
ORDER BY highest_rating DESC;