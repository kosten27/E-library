package com.kostenko.elibrary.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
public class Author extends BaseEntity {

    @NotBlank(message = "Name is mandatory")
    private String name;
    @OneToMany(mappedBy = "author")
    private Set<Book> books;

    public Author() {
    }

    public Author(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public int getNumberOfBooks() {
        return books.size();
    }

    @Override
    public String toString() {
        return name;
    }
}
