package com.cities.cassandra;

import java.text.MessageFormat;

import static com.cities.cassandra.init.CassandraProperties.CASSANDRA_KEYSPACE;

public class CassandraQueryUtil {

    public static final String INSERT_CITY_LIKE_USER = "INSERT INTO " + CASSANDRA_KEYSPACE + "" +
            ".city_like_user (\"city_id\", \"user_id\", \"user_name\") " +
            "VALUES ({0}, {1}, ''{2}'')";

    public static final String SELECT_CITY_LIKE_USER = "SELECT * FROM " + CASSANDRA_KEYSPACE + "" +
            ".city_like_user WHERE city_id = {0}";

    public static final String INSERT_USER_LIKE_CITY = "INSERT INTO " + CASSANDRA_KEYSPACE + "" +
            ".user_like_city (\"user_id\", \"city_id\", \"city_name\") " +
            "VALUES ({0}, {1}, ''{2}'')";

    public static final String SELECT_COMMENTS_OF_CITY_BY_CITY_ID = "SELECT * FROM " + CASSANDRA_KEYSPACE + "" +
            ".city_comments WHERE city_id = {0}";

    public static final String INSERT_COMMENT_TO_CITY = "INSERT INTO " + CASSANDRA_KEYSPACE + "" +
            ".city_comments (\"city_id\", \"comment_id\") " +
            "VALUES ({0}, {1})";

    public static String getQuery(String query, String... placeHolders) {
        MessageFormat messageFormat = new MessageFormat(query);
        return messageFormat.format(placeHolders);
    }
}