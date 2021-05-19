package com.example.springbootonetomanyrestapi2bidirectionnalmysql.jpa;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity


public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String name;

    @Min(value = 1,message = "Please chose a status")
    private int status;

    @OneToMany(mappedBy = "library", cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private Set<Book> books = new HashSet<> ();

    public Set<Book> getBooks () {
        return books;
    }

    public void setBooks (Set<Book> books) {
        this.books = books;
        for (Book b : books) {
            b.setLibrary (this);
        }
    }


    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public int getStatus () {
        return status;
    }

    public void setStatus (int status) {
        this.status = status;
    }
}
