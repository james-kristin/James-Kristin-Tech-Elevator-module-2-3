package com.techelevator.dao;

import com.techelevator.model.Book;

import java.util.List;

public interface BookDao {

    /**
     * Returns all books from the datastore.
     *
     * @return List of all books
     */
    List<Book> getBooks();

    /**
     * Adds a new Book into the datastore.
     *
     * @param newBook the Book object to add
     * @return the Book object with its new id filled in
     */
    Book createBook(Book newBook);
}
