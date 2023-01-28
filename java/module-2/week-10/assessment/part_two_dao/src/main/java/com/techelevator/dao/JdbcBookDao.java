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
        return new ArrayList<>();
    }

    @Override
    public Book createBook(Book newBook) {
        return null;
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
