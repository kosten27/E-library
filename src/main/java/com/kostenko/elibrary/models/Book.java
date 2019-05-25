package com.kostenko.elibrary.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity(name = "BOOK")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @ManyToOne
    private Author author;
    @OneToMany(mappedBy = "book")
    private List<Series> series;

    public Book() {
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

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<Series> getSeries() {
        return series;
    }

    public void setSeries(List<Series> series) {
        this.series = series;
    }

    @Override
    public String toString() {
        return title;
    }
}
