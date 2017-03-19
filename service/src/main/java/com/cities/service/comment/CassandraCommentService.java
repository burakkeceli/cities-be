package com.cities.service.comment;

import com.cities.cassandra.CassandraService;
import com.cities.model.comment.Comment;
import com.cities.model.user.User;
import com.cities.service.comment.model.CassandraCommentModel;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.utils.UUIDs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.cities.cassandra.CassandraQueryUtil.*;

@Component
public class CassandraCommentService {

    @Autowired
    private CassandraService cassandraService;

    public void saveCommentOfCity(User user, Integer cityId, Comment comment) {
        UUID createdTime = UUIDs.startOf(comment.getCreatedTime().getMillis());
        String querySaveCommentOfCity = getQuery(INSERT_COMMENT_TO_CITY,
                cityId.toString(),
                comment.getId().toString(),
                comment.getText(),
                user.getId().toString(),
                user.getUsername(),
                createdTime.toString());
        cassandraService.execute(querySaveCommentOfCity);
    }

    public List<CassandraCommentModel> getCommentsOfCity(Integer cityId) {
        String queryGetCommentsOfCity = getQuery(SELECT_COMMENTS_OF_CITY_BY_CITY_ID, cityId.toString());
        ResultSet resultSet = cassandraService.execute(queryGetCommentsOfCity);
        List<CassandraCommentModel> cassandraCommentModelList = new ArrayList<>();

        for (Row row : resultSet) {
            Integer fetchedCityId = row.getInt("city_id");
            Integer fetchedCommentId = row.getInt("comment_id");
            String fetchedCommentText = row.getString("comment_text");
            Integer fetchedUserId = row.getInt("user_id");
            String fetchedUserName = row.getString("user_name");
            UUID fetchedCreatedTime = row.getUUID("created_time");
            CassandraCommentModel cassandraCommentModel = new CassandraCommentModel(fetchedCityId, fetchedCommentId, fetchedCommentText, fetchedUserId, fetchedUserName, fetchedCreatedTime);
            cassandraCommentModelList.add(cassandraCommentModel);
        }

        return cassandraCommentModelList;
    }
}
