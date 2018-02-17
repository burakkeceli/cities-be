package com.cities.twitter;

import com.cities.helper.JacksonService;
import com.cities.model.twitter.TwitterSearchMetadata;
import com.cities.model.twitter.TwitterSearchModel;
import com.cities.model.twitter.TwitterSearchModelList;
import com.cities.model.twitter.TwitterSearchStatusModel;
import com.cities.test.AbstractBaseTest;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.joda.time.DateTime.now;

public class RedisTwitterServiceITest extends AbstractBaseTest {
    private static final Integer USER_ID = 1;

    @Autowired
    private StringRedisTemplate strRedisTemplate;
    @Autowired
    private RedisTwitterService redisTwitterService;
    @Autowired
    private JacksonService jacksonService;

    @Before
    @After
    public void destroy() {
        strRedisTemplate.delete(USER_ID.toString());
    }

    @Test
    public void shouldSaveTwitterSearchModelInRedis() throws Exception {
        // given
        List<TwitterSearchStatusModel> twitterSearchStatusModelList = new ArrayList<>();
        TwitterSearchMetadata searchMetadata = new TwitterSearchMetadata();

        TwitterSearchModelList modelList = new TwitterSearchModelList(twitterSearchStatusModelList, searchMetadata);

        // and
        DateTime searchTime = now();
        TwitterSearchModel searchModel = new TwitterSearchModel(modelList, USER_ID, searchTime);

        // when
        redisTwitterService.saveResult(searchModel);

        // then
        HashOperations<String, String, String> redisHash = strRedisTemplate.opsForHash();
        Map<String, String> entries = redisHash.entries(USER_ID.toString());

        assertThat(entries).hasSize(1);
        assertThat(entries).containsKeys(jacksonService.toJson(searchTime));
        assertThat(entries).containsValue(jacksonService.toJson(modelList));
    }
}
