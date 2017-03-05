package com.cities.service.comment;


import com.cities.base.AbstractServiceBaseITest;
import com.cities.helper.BaseTestHelper;
import com.cities.model.city.City;
import com.cities.model.comment.Comment;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.joda.time.DateTime.now;

public class CassandraCommentServiceITest extends AbstractServiceBaseITest {

    @Autowired
    private BaseTestHelper baseTestHelper;
    @Autowired
    private CassandraCommentService cassandraCommentService;

    @Test
    public void shouldSaveCommentToCity() {
        // given
        String text = UUID.randomUUID().toString();
        int userId = 1;

        Comment comment = baseTestHelper.saveComment(userId, text, now());
        City city = baseTestHelper.saveCity(1, UUID.randomUUID().toString());

        // when
        cassandraCommentService.saveCommentOfCity(city.getId(), comment.getId());

        // then
        Map<Integer, Integer> cityCommentMap = cassandraCommentService.getCommentsOfCity(city.getId());
        assertThat(cityCommentMap).containsEntry(city.getId(), comment.getId());
    }
}
