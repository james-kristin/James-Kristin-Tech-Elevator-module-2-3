-- 17. The titles and taglines of movies that are in the "Family" genre that Samuel L. Jackson has acted in.
-- Order the results alphabetically by movie title.
-- (4 rows)

SELECT title, tagline
FROM movie
JOIN movie_actor ON movie_actor.movie_id = movie.movie_id
JOIN person ON actor_id = person_id
JOIN movie_genre ON movie_genre.movie_id = movie.movie_id
JOIN genre ON genre.genre_id = movie_genre.genre_id
WHERE genre_name = 'Family' AND person_name = 'Samuel L. Jackson'
ORDER BY title;
