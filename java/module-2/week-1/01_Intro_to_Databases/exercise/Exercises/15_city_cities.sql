-- 15. The name, state abbreviation, and population for cities that end with the word "City" (11 rows)

SELECT city_name, state_abbreviation, population
FROM city
Where city_name LIKE '%City';