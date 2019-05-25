package com.kostenko.elibrary.models;

import javax.persistence.*;

@Entity
public class BookOrderLine {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private BookOrder bookOrder;
    @OneToOne
    private Book book;
    @OneToOne
    private Series series;

    public BookOrderLine() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BookOrder getBookOrder() {
        return bookOrder;
    }

    public void setBookOrder(BookOrder bookOrder) {
        this.bookOrder = bookOrder;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    @Override
    public String toString() {
        return book + " (" + series + ")";
    }
}
