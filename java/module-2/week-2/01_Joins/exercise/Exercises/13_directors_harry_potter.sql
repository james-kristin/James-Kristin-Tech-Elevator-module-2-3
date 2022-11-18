-- 13. The directors of the movies in the "Harry Potter Collection", sorted alphabetically.
-- (4 rows)

SELECT DISTINCT person_name 
FROM person
JOIN movie ON director_id = person_id
Join collection ON collection.collection_id = movie.collection_id
WHERE collection_name = 'Harry Potter Collection'
ORDER BY person_name;