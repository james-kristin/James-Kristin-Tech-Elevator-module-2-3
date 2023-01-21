package com.techelevator.dao;

import com.techelevator.model.Book;

import java.math.BigDecimal;
import java.util.List;

public interface BookDao {

    /**
     * Returns a specific book from the datastore.
     *
     * @param bookId the id of the book to retrieve
     * @return The book object that corresponds to the given id
     */
    Book getBook(int bookId);

    /**
     * Adds a new Book into the datastore.
     *
     * @param bookId the new book to update
     * @param newRating the new rating for the book
     * @return the Book object with its updated rating
     */
    Book updateRating(int bookId, BigDecimal newRating);

    /**
     * Removes a new Book from the datastore.
     *
     * @param bookId the new book to update
     */
    void deleteBook(int bookId);
}
