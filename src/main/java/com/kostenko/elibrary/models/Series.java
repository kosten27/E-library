package com.kostenko.elibrary.models;

import javax.persistence.*;

@Entity
public class Series  extends BaseEntity {

    private String name;
    private boolean inStock;
    @ManyToOne
    private Book book;

    public Series() {
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", book, name);
    }
}
