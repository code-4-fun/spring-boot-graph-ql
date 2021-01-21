package com.example.graphql.demo.springbootgraphql.query;

import com.example.graphql.demo.springbootgraphql.entity.Author;
import com.example.graphql.demo.springbootgraphql.entity.Book;

import java.util.Set;

public class BookInfo {
    private Book book;
    private Set<Author> authors;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }
}
