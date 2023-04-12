/**
 * Write a function that takes in an array of numbers and totals all the numbers between 0 and 100. 
 * Ignore any values less than 0 and any values greater than 100.
 * 
 * Assume all values are numeric or, as an extra challenge, ignore any values that are not numeric.
 * 
 * Example 1: filteredSum([-3, 0, 100, 830, 6, -2]) --> 106 (all values except 100 and 6 are ignored)
 * Example 2: filteredSum([-2, -300, 0, 702]) --> 0 (all values ignored)
 * Example 3: filteredSum([]) --> 0
 */
function filteredSum(arr) {
    let result = 0;
    for (const value of arr) {
        // Optional validation for numeric
        if (parseInt(value) !== NaN) {
            // Sum values greater than 0 and less than or equal to 100
            if (value > 0 && value <= 100) {
                result = result + value;
            }
        }
    }
    return result;
}

/**
 * Given a string for the year and an array of book objects with properties for 
 * title, genres, number of pages, and an array of dates read, determine the total number 
 * of pages of both fiction and non-fiction books read for a given year. 
 * 
 * The genre array is a list of strings that will include either "Fiction" or not. 
 *   If it does count the book as fiction, otherwise count the book as non-fiction.
 * The array of dates read are strings in the format of MM-DD-YYYY. 
 * 
 * Book objects have properties for title, genres, pageCount, and datesRead.
 * Return an object with properties for pagesFiction and pagesNonFiction.
 *   For example: {
 *     pagesFiction: 1259
 *     pagesNonFiction: 927
 *   }
 */
function yearlyBookCount(year, bookArray) {
    let result = {
        pagesFiction: 0,
        pagesNonFiction: 0
    }
    for (const book of bookArray) {
        // Loop over the array of dates looking for a matching year
        for (const date of book.datesRead) {
            const readYear = date.substr(6, 4);
            if (year === readYear) {
                // The year matches, so add to the appropriate count
                if (book.genres.includes('Fiction')) {
                    result.pagesFiction += book.pageCount;
                } else {
                    result.pagesNonFiction += book.pageCount;
                }
            }
        }
    }
    return result;
}

/**
 * OPTIONAL: For an additional challenge, given a string year and the same array of book objects from the previous problem, 
 * return an object with counts for the total number of books read and the counts for each genre. 
 * 
 * Note that the same book may be counted multiple times in different genres. This means the total book count may be smaller
 * than the sum of all the genre counts. 
 * 
 * Book objects have properties for title, genres, pageCount, and datesRead.
 * Return an object with properties for the total number of books read for the given year plus a count for each genre found, 
 * using the genre as the property name and the count as the property value.
 * For example: {
 *    Biography: 6, 
 *    Fantasy: 11
 *    Fiction: 23,
 *    History: 4,
 *    Non-Fiction: 9,
 *    Poetry: 3,
 *    Science-Fiction: 17,
 *    totalBooks: 32
 * }
 */
function yearlyBookStatistics(year, bookArray) {
    let result = {
        totalBooks: 0
    }
    
    for (const book of bookArray) {
        // Loop over the array of dates looking for a matching year
        for (const date of book.datesRead) {
            const readYear = date.substr(6, 4);
            if (year === readYear) {
                // The year matches, so add to the total count
                result.totalBooks++;
                // Loop over the genre array adding to each genre count
                for (const genre of book.genres) {
                    if (result[genre] === undefined) {
                        // If the property for that genre doesn't exist, create it
                        result[genre] = 1;
                    } else {
                        result[genre]++;
                    }
                }
            }
        }
    }
    return result;
}