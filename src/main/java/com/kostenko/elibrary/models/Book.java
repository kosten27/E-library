package com.kostenko.elibrary.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity(name = "BOOK")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Title is mandatory")
    private String title;
    @ManyToOne
    private Author author;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
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

    public void setId(Long id) {
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

    public int getNumberOfSeries() {
        return series.size();
    }

    @Override
    public String toString() {
        return title;
    }
}
