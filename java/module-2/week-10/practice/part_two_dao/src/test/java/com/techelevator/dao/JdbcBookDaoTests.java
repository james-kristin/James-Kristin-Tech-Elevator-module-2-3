package com.techelevator.dao;

import com.techelevator.model.Book;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

public class JdbcBookDaoTests extends BaseDaoTests {
    
    private static final Book BOOK_1 = new Book(1, "BOOK 1", new BigDecimal("5.0"), false, 2, 1, LocalDate.parse("2022-01-01"));
    
    private JdbcBookDao dao;

    private final int INVALID_BOOK_ID = 999;

    private Book testBook;
    
    @Before
    public void setup() {
        dao = new JdbcBookDao(dataSource);
        testBook = null;
    }

    @Test
    public void getBook_returns_specified_book() {
        testBook = dao.getBook(BOOK_1.getBookId());
        assertBooksMatch("getBook(bookId) returned wrong or partial data.", BOOK_1, testBook);
    }

    @Test
    public void getBook_returns_default_book_if_not_found() {
        testBook = dao.getBook(INVALID_BOOK_ID);
        assertBooksMatch("getBook(bookId) should return default book object if not found." , new Book(), testBook);
    }

    @Test
    public void updateRating_return_book_with_new_rating() {
        BigDecimal newRating =  new BigDecimal("3.0");
        Book testBook = dao.updateRating(BOOK_1.getBookId(), newRating);
        Book updatedBook = dao.getBook(BOOK_1.getBookId());
        Assert.assertEquals("updateRating(bookId, newRating) did not return a book object with the updated starRating", testBook.getStarRating(), updatedBook.getStarRating());
    }

    @Test
    public void deleteBook_removes_book() {
        // test that book exists in dao
        testBook = dao.getBook(BOOK_1.getBookId());
        assertBooksMatch("failed to confirm book exists in dao BEFORE delete", BOOK_1, testBook);
        dao.deleteBook(BOOK_1.getBookId());
        // test that book no longer exists in dao
        testBook = dao.getBook(BOOK_1.getBookId());
        assertBooksMatch("deleteBook(bookId) failed to remove the correct book", new Book(), testBook);
    }

    private void assertBooksMatch(String message, Book expected, Book actual) {
        Assert.assertEquals(message, expected.getBookId(), actual.getBookId());
        Assert.assertEquals(message, expected.getBookTitle(), actual.getBookTitle());
        Assert.assertEquals(message, expected.getStarRating(), actual.getStarRating());
        Assert.assertEquals(message, expected.isOutOfPrint(), actual.isOutOfPrint());
        Assert.assertEquals(message, expected.getForewordBy(), actual.getForewordBy());
        Assert.assertEquals(message, expected.getPublisherId(), actual.getPublisherId());
        Assert.assertEquals(message, expected.getPublishedDate(), actual.getPublishedDate());
    }
}
