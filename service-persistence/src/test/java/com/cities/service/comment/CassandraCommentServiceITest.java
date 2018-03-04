package com.cities.service.comment;


import com.cities.helper.BaseTestHelper;
import com.cities.model.city.City;
import com.cities.model.comment.Comment;
import com.cities.model.user.User;
import com.cities.service.comment.model.CassandraCommentModel;
import com.cities.test.AbstractBaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.joda.time.DateTime.now;

public class CassandraCommentServiceITest extends AbstractBaseTest {

    @Autowired
    private BaseTestHelper baseTestHelper;
    @Autowired
    private CassandraCommentService cassandraCommentService;
/*
    @Test
    public void shouldSaveCommentToCity() {
        // given
        String text = randomUUID().toString();

        User user = baseTestHelper.saveUser(randomUUID().toString());
        Comment comment = baseTestHelper.saveComment(user.getId(), text, now());
        City city = baseTestHelper.saveCity(1, randomUUID().toString());

        // when
        cassandraCommentService.saveCommentOfCity(user, city.getId(), comment);

        // then
        List<CassandraCommentModel> cassandraCommentModelList = cassandraCommentService.getCommentsOfCity(city.getId());
        assertThat(cassandraCommentModelList).hasSize(1);
        assertFetchedCassandraCommentModel(user, city, cassandraCommentModelList.get(0), text);
    }

    @Test
    public void shouldSaveSeveralCommentsOfSameUserToCity() {
        // given
        String text1 = randomUUID().toString();
        String text2 = randomUUID().toString();

        User user = baseTestHelper.saveUser(randomUUID().toString());
        City city = baseTestHelper.saveCity(1, randomUUID().toString());
        Comment comment1 = baseTestHelper.saveComment(user.getId(), text1, now());
        Comment comment2 = baseTestHelper.saveComment(user.getId(), text2, now());

        // when
        cassandraCommentService.saveCommentOfCity(user, city.getId(), comment1);
        cassandraCommentService.saveCommentOfCity(user, city.getId(), comment2);

        // then
        List<CassandraCommentModel> cassandraCommentModelList = cassandraCommentService.getCommentsOfCity(city.getId());
        assertThat(cassandraCommentModelList).hasSize(2);

        CassandraCommentModel cassandraCommentModel1 = cassandraCommentModelList.stream().filter(comment -> comment.getCommentId().equals(comment1.getId())).findFirst().get();
        assertFetchedCassandraCommentModel(user, city, cassandraCommentModel1, text1);

        CassandraCommentModel cassandraCommentModel2 = cassandraCommentModelList.stream().filter(comment -> comment.getCommentId().equals(comment2.getId())).findFirst().get();
        assertFetchedCassandraCommentModel(user, city, cassandraCommentModel2, text2);
    }

    private void assertFetchedCassandraCommentModel(User user,
                                                    City city,
                                                    CassandraCommentModel cassandraCommentModel,
                                                    String text) {
        assertThat(cassandraCommentModel.getUserId()).isEqualTo(user.getId());
        assertThat(cassandraCommentModel.getCityId()).isEqualTo(city.getId());
        assertThat(cassandraCommentModel.getUserName()).isEqualTo(user.getUsername());
        assertThat(cassandraCommentModel.getCommentText()).isEqualTo(text);
    }*/
}
