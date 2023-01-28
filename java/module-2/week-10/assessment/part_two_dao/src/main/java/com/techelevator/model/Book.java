package com.techelevator.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Book {
    private int bookId;
    private String bookTitle;
    private BigDecimal starRating;
    private boolean outOfPrint;
    private Integer forewordBy; // Integer instead of int because value may be NULL
    private int publisherId;
    private LocalDate publishedDate;

    public Book() {}

    public Book(int bookId, String bookTitle, BigDecimal starRating, boolean outOfPrint, Integer forewordBy, int publisherId, LocalDate publishedDate) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.starRating = starRating;
        this.outOfPrint = outOfPrint;
        this.forewordBy = forewordBy;
        this.publisherId = publisherId;
        this.publishedDate = publishedDate;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public BigDecimal getStarRating() {
        return starRating;
    }

    public void setStarRating(BigDecimal starRating) {
        this.starRating = starRating;
    }

    public boolean isOutOfPrint() {
        return outOfPrint;
    }

    public void setOutOfPrint(boolean outOfPrint) {
        this.outOfPrint = outOfPrint;
    }

    public Integer getForewordBy() {
        return forewordBy;
    }

    public void setForewordBy(Integer forewordBy) {
        this.forewordBy = forewordBy;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }
}
