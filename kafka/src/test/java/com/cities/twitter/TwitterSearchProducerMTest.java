package com.cities.twitter;

import com.cities.city.CityProducer;
import com.cities.helper.JacksonService;
import com.cities.model.city.City;
import com.cities.model.twitter.TwitterSearchMetadata;
import com.cities.model.twitter.TwitterSearchModel;
import com.cities.model.twitter.TwitterSearchModelList;
import com.cities.model.twitter.TwitterSearchStatusModel;
import com.cities.service.city.CityService;
import com.cities.test.AbstractBaseTest;
import org.assertj.core.api.Assertions;
import org.joda.time.DateTime;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.joda.time.DateTime.now;

public class TwitterSearchProducerMTest extends AbstractBaseTest {

    private static final Integer USER_ID = 1;

    @Autowired
    private TwitterSearchProducer twitterSearchProducer;
    @Autowired
    private StringRedisTemplate strRedisTemplate;
    @Autowired
    private JacksonService jacksonService;

    @Test
    public void shouldSaveCityAsync() throws Exception {
        // given
        List<TwitterSearchStatusModel> twitterSearchStatusModelList = new ArrayList<>();
        TwitterSearchMetadata searchMetadata = new TwitterSearchMetadata();

        TwitterSearchModelList modelList = new TwitterSearchModelList(twitterSearchStatusModelList, searchMetadata);

        // and
        DateTime searchTime = now();
        TwitterSearchModel searchModel = new TwitterSearchModel(modelList, USER_ID);

        // when
        twitterSearchProducer.sendTwitterSearchMessage(searchModel);

        // wait
        Thread.sleep(3_000);

        // then
        HashOperations<String, String, String> redisHash = strRedisTemplate.opsForHash();
        Map<String, String> entries = redisHash.entries(USER_ID.toString());

        assertThat(entries).hasSize(1);
        assertThat(entries).containsKeys(jacksonService.toJson(searchTime.toString()));
        assertThat(entries).containsValue(jacksonService.toJson(modelList));
    }
}
