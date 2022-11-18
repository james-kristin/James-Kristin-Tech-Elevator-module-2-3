-- 7. The genres of movies that Christopher Lloyd has appeared in, sorted alphabetically.
-- (8 rows) Hint: DISTINCT will prevent duplicate values in your query results.

SELECT DISTINCT genre.genre_name
FROM movie 
JOIN movie_genre ON movie_genre.movie_id = movie.movie_id
JOIN genre ON genre.genre_id = movie_genre.genre_id
JOIN movie_actor ON movie_actor.movie_id = movie.movie_id
JOIN person ON person_id = actor_id
WHERE person_name = 'Christopher Lloyd'
ORDER BY genre.genre_name;
