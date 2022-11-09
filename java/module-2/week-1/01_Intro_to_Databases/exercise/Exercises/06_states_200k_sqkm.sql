-- 6. The name, abbreviation, population, and area of states with an area greater than 200,000 square kilometers (16 rows)

SELECT state_name, state_abbreviation, population, area 
FROM state
Where area > 200000;