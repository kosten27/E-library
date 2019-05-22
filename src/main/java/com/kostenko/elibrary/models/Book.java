package com.kostenko.elibrary.models;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "BOOK")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    @ManyToOne
    private Author author;
//    private int year;

    public Book() {
    }

    public Book(String title, Set<Author> authors) {
        this.title = title;
        this.author = author;
//        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public Author getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

//    public int getYear() {
//        return year;
//    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

//    public void setYear(int year) {
//        this.year = year;
//    }

    @Override
    public String toString() {
        return title;
    }
}
