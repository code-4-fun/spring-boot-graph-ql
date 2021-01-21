package com.example.graphql.demo.springbootgraphql.entity;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Objects;

import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.CLUSTERED;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;

@Table("relationship")
public class Relationship {

    @PrimaryKeyColumn(type = PARTITIONED)
    @Column("query_key")
    private String queryKey;

    @PrimaryKeyColumn(type = PARTITIONED)
    @Column("query_id")
    private String queryId;

    @PrimaryKeyColumn(type = CLUSTERED)
    @Column("target_key")
    private String targetKey;

    @PrimaryKeyColumn(type = CLUSTERED)
    @Column("target_id")
    private String targetId;

    public String getQueryKey() {
        return queryKey;
    }

    public void setQueryKey(String queryKey) {
        this.queryKey = queryKey;
    }

    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public String getTargetKey() {
        return targetKey;
    }

    public void setTargetKey(String targetKey) {
        this.targetKey = targetKey;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Relationship that = (Relationship) o;
        return Objects.equals(queryKey, that.queryKey) &&
                Objects.equals(queryId, that.queryId) &&
                Objects.equals(targetKey, that.targetKey) &&
                Objects.equals(targetId, that.targetId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(queryKey, queryId, targetKey, targetId);
    }
}
