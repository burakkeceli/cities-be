package com.cities.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.AlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.cities.cassandra.init.CassandraProperties.CASSANDRA_KEYSPACE;
import static com.cities.cassandra.init.CassandraProperties.DEFAULT_REPLICATION_FACTOR;
import static com.cities.cassandra.init.CassandraTables.TABLES;

@Slf4j
@Component
public class CassandraService {

    private static final String CREATE_KEYSPACE = "CREATE KEYSPACE " + CASSANDRA_KEYSPACE+ " WITH REPLICATION = {'class': 'SimpleStrategy', 'replication_factor': " + DEFAULT_REPLICATION_FACTOR + "}";

    private Cluster cluster;
    private Session session;

    @Value("${cassandra.contact.point}")
    private String cassandraContactPoint;

    @PostConstruct
    public void init() {
        // TODO: read contact point from a property service
        cluster = Cluster.builder()
                .addContactPoint(cassandraContactPoint)
                .build();

        session = cluster.connect();
        createKeyspace();
        createTables();
    }

    private void createKeyspace() {
        try {
            execute(CREATE_KEYSPACE);
        } catch (AlreadyExistsException e) {
            log.warn("keyspace already exists");
        }
    }

    private void createTables() {
        for (String table : TABLES) {
            try {
                execute(table);
            } catch (AlreadyExistsException e) {
                log.warn("table already exists");
            }
        }
    }

    public ResultSet execute(String query) {
        return session.execute(query);
    }
}
