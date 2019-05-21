package com.kostenko.elibrary.models;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "BOOK")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    @ManyToMany
    private Set<Author> authors;
    private int year;

    public Book() {
    }

    public Book(String title, Set<Author> authors, int year) {
        this.title = title;
        this.authors = authors;
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public Set<Author> getAuthor() {
        return authors;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(Set<Author> authors) {
        this.authors = authors;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return title;
    }
}
