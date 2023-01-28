package com.techelevator.dao;

import com.techelevator.model.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcBookDao implements BookDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcBookDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Book> getBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT book_id, book_title, star_rating, out_of_print, foreword_by, publisher_id, published_date " +
                "FROM book";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Book book = mapRowToBook(results);
            books.add(book);
        }
        return books;
    }

    @Override
    public Book createBook(Book newBook) {
        String sql = "INSERT INTO book (book_title, star_rating, out_of_print, foreword_by, publisher_id, published_date) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING book_id";
        int bookID = jdbcTemplate.queryForObject(sql, int.class, newBook.getBookTitle(), newBook.getStarRating(), newBook.isOutOfPrint(), newBook.getForewordBy(), newBook.getPublisherId(), newBook.getPublishedDate());
        newBook.setBookId(bookID);
        return newBook;
    }

    private Book mapRowToBook(SqlRowSet results) {
        Book book = new Book();
        book.setBookId(results.getInt("book_id"));
        book.setBookTitle(results.getString("book_title"));
        book.setStarRating(results.getBigDecimal("star_rating"));
        book.setOutOfPrint(results.getBoolean("out_of_print"));
        book.setForewordBy((Integer) results.getObject("foreword_by"));
        book.setPublisherId(results.getInt("publisher_id"));
        book.setPublishedDate(results.getDate("published_date").toLocalDate());
        return book;
    }
}
