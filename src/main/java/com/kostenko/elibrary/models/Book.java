package com.kostenko.elibrary.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "BOOK")
public class Book  extends BaseEntity {

    @NotBlank(message = "Title is mandatory")
    private String title;
    @ManyToOne
    @NotNull
    private Author author;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Series> series;

    public Book() {
    }

    public Author getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
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
