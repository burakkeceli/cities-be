package com.cities.service.comment;

import com.cities.cassandra.CassandraService;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.cities.cassandra.CassandraQueryUtil.INSERT_COMMENT_TO_CITY;
import static com.cities.cassandra.CassandraQueryUtil.SELECT_COMMENTS_OF_CITY_BY_CITY_ID;
import static com.cities.cassandra.CassandraQueryUtil.getQuery;

@Component
public class CassandraCommentService {

    @Autowired
    private CassandraService cassandraService;

    public void saveCommentOfCity(Integer cityId, Integer commentId) {
        String querySaveCommentOfCity = getQuery(INSERT_COMMENT_TO_CITY, cityId.toString(), commentId.toString());
        cassandraService.execute(querySaveCommentOfCity);
    }

    public Map<Integer, Integer> getCommentsOfCity(Integer cityId) {
        String queryGetCommentsOfCity = getQuery(SELECT_COMMENTS_OF_CITY_BY_CITY_ID, cityId.toString());
        ResultSet resultSet = cassandraService.execute(queryGetCommentsOfCity);
        Map<Integer, Integer> cityCommentMap = new HashMap<>();

        for (Row row : resultSet) {
            Integer fetchedCityId = row.getInt("city_id");
            Integer fetchedCommentId = row.getInt("comment_id");
            cityCommentMap.put(fetchedCityId, fetchedCommentId);
        }

        return cityCommentMap;
    }
}
