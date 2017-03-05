package com.cities.cassandra.init;

import static com.cities.cassandra.init.CassandraProperties.CASSANDRA_KEYSPACE;

public class CassandraTables {

    public static final String[] TABLES = {
            "create table " + CASSANDRA_KEYSPACE + ".city_like_user ("
                    + " city_id int," +
                    " user_id int," +
                    " user_name text," +
                    " primary key ((city_id), user_id)" +
                    ")",

            "create table " + CASSANDRA_KEYSPACE + ".user_like_city ("
                    + " user_id int," +
                    " city_id int," +
                    " city_name text," +
                    " primary key ((user_id), city_id)" +
                    ")",

            "create table " + CASSANDRA_KEYSPACE + ".city_comments ("
                    + " city_id int," +
                    " comment_id int," +
                    " primary key ((city_id))" +
                    ")"
    };
}
