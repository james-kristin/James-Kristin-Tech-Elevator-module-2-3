package com.techelevator.dao;

import com.techelevator.model.Book;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JdbcBookDaoTests extends BaseDaoTests {
    
    private static final Book BOOK_1 = new Book(1, "BOOK 1", new BigDecimal("5.0"), false, 2, 1, LocalDate.parse("2022-01-01"));
    private static final Book BOOK_2 = new Book(2, "BOOK 2", new BigDecimal("4.9"), false, 1, 2, LocalDate.parse("2022-02-02"));
    private static final Book BOOK_3 = new Book(3, "BOOK 3", new BigDecimal("2.5"), true, null, 1, LocalDate.parse("2022-03-03"));
    private static final Book BOOK_4 = new Book(4, "BOOK 4", new BigDecimal("0.0"), true, null, 2, LocalDate.parse("2022-04-04"));
    
    private JdbcBookDao dao;

    private final List<Book> testBooks = new ArrayList<>(Arrays.asList(BOOK_1, BOOK_2, BOOK_3, BOOK_4));
    private Book testNewBook;
    
    @Before
    public void setup() {
        dao = new JdbcBookDao(dataSource);
        testNewBook = new Book(99, "Test book", new BigDecimal("3.7"), false, 1, 1, LocalDate.now());
    }

    @Test
    public void getBooks_returns_all_books() {
        List<Book> books = dao.getBooks();
        Assert.assertEquals("getBooks returned wrong number of books", testBooks.size(), books.size());
        assertBooksMatch("getBooks returned wrong or partial data", BOOK_1, books.get(0));
        assertBooksMatch("getBooks returned wrong or partial data", BOOK_2, books.get(1));
        assertBooksMatch("getBooks returned wrong or partial data", BOOK_3, books.get(2));
        assertBooksMatch("getBooks returned wrong or partial data", BOOK_4, books.get(3));
    }

    @Test
    public void createBook_returns_new_book_id() {
        Book createdBook = dao.createBook(testNewBook);

        Assert.assertNotNull("createBook returned null", createdBook);

        Assert.assertTrue("createBook failed to return the correct book id", createdBook.getBookId() == testBooks.size() + 1 );
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
