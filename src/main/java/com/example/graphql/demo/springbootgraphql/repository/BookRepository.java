package com.example.graphql.demo.springbootgraphql.repository;

import com.example.graphql.demo.springbootgraphql.entity.Book;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;

public interface BookRepository extends CassandraRepository<Book, UUID> {
}
