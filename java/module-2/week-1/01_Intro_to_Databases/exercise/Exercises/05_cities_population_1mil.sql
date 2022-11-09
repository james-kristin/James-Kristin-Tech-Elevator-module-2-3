-- 5. The name, state abbreviation, and population of cities with a population greater than 1,000,000 people (10 rows)

select city_name, state_abbreviation, population
FROM city
Where population > 1000000;