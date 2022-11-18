-- 8. The genres of movies that Robert De Niro has appeared in that were released in 2010 or later, sorted alphabetically.
-- (6 rows)

SELECT DISTINCT genre.genre_name
FROM movie 
JOIN movie_genre ON movie_genre.movie_id = movie.movie_id
JOIN genre ON genre.genre_id = movie_genre.genre_id
JOIN movie_actor ON movie_actor.movie_id = movie.movie_id
JOIN person ON person_id = actor_id
WHERE person_name = 'Robert De Niro' AND release_date >= '2010-01-01'
ORDER BY genre.genre_name;