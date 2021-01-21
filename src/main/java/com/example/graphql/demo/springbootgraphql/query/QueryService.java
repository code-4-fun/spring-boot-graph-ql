package com.example.graphql.demo.springbootgraphql.query;

import com.example.graphql.demo.springbootgraphql.entity.Author;
import com.example.graphql.demo.springbootgraphql.entity.Book;
import com.example.graphql.demo.springbootgraphql.entity.Relationship;
import com.example.graphql.demo.springbootgraphql.repository.AuthorRepository;
import com.example.graphql.demo.springbootgraphql.repository.BookRepository;
import com.example.graphql.demo.springbootgraphql.repository.RelationshipRepository;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@GraphQLApi
public class QueryService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final RelationshipRepository relationshipRepository;

    public QueryService(BookRepository bookRepository, AuthorRepository authorRepository, RelationshipRepository relationshipRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.relationshipRepository = relationshipRepository;
    }

    @GraphQLQuery(name = "getAllBooks", description = "Fetch All Books")
    public List<BookInfo> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::buildBookInfoFromBook)
                .collect(Collectors.toList());
    }

    @GraphQLQuery(name = "getAllAuthors", description = "Find All Authors")
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @GraphQLQuery(name = "findBooksByAuthor", description = "Find All Books for Author")
    public List<BookInfo> findBooksByAuthor(String author) {
        final List<Relationship> relationships = relationshipRepository.findByQueryKeyAndQueryIdAndTargetKey("author", author, "bookId");
        return relationships
                .stream()
                .map(relationship -> bookRepository.findById(UUID.fromString(relationship.getTargetId())))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(this::buildBookInfoFromBook)
                .collect(Collectors.toList())
                ;
    }

    private BookInfo buildBookInfoFromBook(Book book) {
        final List<Relationship> authorsRelations = relationshipRepository.findByQueryKeyAndQueryIdAndTargetKey("bookId", book.getId().toString(), "author");
        final Set<Author> authors =
                authorsRelations
                        .stream()
                        .map(rel -> {
                            final Author authorRecord = new Author();
                            authorRecord.setName(rel.getTargetId());
                            return authorRecord;
                        }).collect(Collectors.toSet());

        final BookInfo info = new BookInfo();
        info.setBook(book);
        info.setAuthors(authors);
        return info;
    }

}
