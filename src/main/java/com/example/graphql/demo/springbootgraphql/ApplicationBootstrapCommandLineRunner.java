package com.example.graphql.demo.springbootgraphql;

import com.datastax.oss.driver.shaded.guava.common.collect.ImmutableSet;
import com.example.graphql.demo.springbootgraphql.entity.Author;
import com.example.graphql.demo.springbootgraphql.entity.Book;
import com.example.graphql.demo.springbootgraphql.entity.Relationship;
import com.example.graphql.demo.springbootgraphql.query.BookInfo;
import com.example.graphql.demo.springbootgraphql.repository.AuthorRepository;
import com.example.graphql.demo.springbootgraphql.repository.BookRepository;
import com.example.graphql.demo.springbootgraphql.repository.RelationshipRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ApplicationBootstrapCommandLineRunner implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final RelationshipRepository relationshipRepository;

    public ApplicationBootstrapCommandLineRunner(BookRepository bookRepository, AuthorRepository authorRepository, RelationshipRepository relationshipRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.relationshipRepository = relationshipRepository;
    }

    @Override
    public void run(String... args) {

        final Set<BookInfo> books = getBooks();

        books.stream().map(BookInfo::getBook).forEach(bookRepository::save);
        books.stream().map(BookInfo::getAuthors).forEach(authorRepository::saveAll);

        books
                .stream()
                .map(book -> {
                    final String bookId = book.getBook().getId().toString();
                    final Set<Relationship> relationships =
                            book.getAuthors()
                                    .stream()
                                    .map(author -> {
                                        final Relationship relToAuthor = new Relationship();
                                        relToAuthor.setQueryKey("author");
                                        relToAuthor.setQueryId(author.getName());
                                        relToAuthor.setTargetKey("bookId");
                                        relToAuthor.setTargetId(bookId);

                                        final Relationship relToBook = new Relationship();
                                        relToBook.setQueryKey("bookId");
                                        relToBook.setQueryId(bookId);
                                        relToBook.setTargetKey("author");
                                        relToBook.setTargetId(author.getName());

                                        return ImmutableSet.of(relToAuthor, relToBook);
                                    })
                                    .flatMap(Collection::stream)
                                    .collect(Collectors.toSet());

                    final Relationship relToBookName = new Relationship();
                    relToBookName.setQueryKey("bookName");
                    relToBookName.setQueryId(book.getBook().getName());
                    relToBookName.setTargetKey("bookId");
                    relToBookName.setTargetId(bookId);

                    relationships.add(relToBookName);

                    return relationships;
                }).forEach(relationshipRepository::saveAll);

    }

    private Set<BookInfo> getBooks() {
        // authors
        final Author author1 = new Author();
        author1.setId(UUID.randomUUID());
        author1.setName("Author 1");

        final Author author2 = new Author();
        author2.setId(UUID.randomUUID());
        author2.setName("Author 2");

        final Author author3 = new Author();
        author3.setId(UUID.randomUUID());
        author3.setName("Author 3");

        // books
        final Book book1 = new Book();
        book1.setId(UUID.randomUUID());
        book1.setName("Some Book");
        book1.setPrice(100.05F);

        final BookInfo info1 = new BookInfo();
        info1.setBook(book1);
        info1.setAuthors(ImmutableSet.of(author1, author2));

        final Book book2 = new Book();
        book2.setId(UUID.randomUUID());
        book2.setName("Another Book");
        book2.setPrice(150F);

        final BookInfo info2 = new BookInfo();
        info2.setBook(book2);
        info2.setAuthors(ImmutableSet.of(author1, author3));

        return ImmutableSet.of(info1, info2);
    }

}
