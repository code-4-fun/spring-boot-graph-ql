package com.example.graphql.demo.springbootgraphql.repository;

import com.example.graphql.demo.springbootgraphql.entity.Author;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;

public interface AuthorRepository extends CassandraRepository<Author, UUID> {
}
