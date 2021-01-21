package com.example.graphql.demo.springbootgraphql.repository;

import com.example.graphql.demo.springbootgraphql.entity.Relationship;
import org.springframework.data.cassandra.repository.MapIdCassandraRepository;

import java.util.List;

public interface RelationshipRepository extends MapIdCassandraRepository<Relationship> {
    
    List<Relationship> findByQueryKeyAndQueryIdAndTargetKey(String queryKey, String queryId, String targetKey);

}
