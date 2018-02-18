package com.cities.twitter;

import com.cities.helper.JacksonService;
import com.cities.model.twitter.TwitterSearchModel;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisTwitterService {

    @Autowired
    private StringRedisTemplate strRedisTemplate;
    @Autowired
    private JacksonService jacksonService;

    public void saveResult(TwitterSearchModel searchModel) {
        HashOperations<String, String, String> redisHash = strRedisTemplate.opsForHash();
        redisHash.putAll(searchModel.getUserId().toString(),
                ImmutableMap.of(jacksonService.toJson(searchModel.getSearchTime()),
                        jacksonService.toJson(searchModel.getSearchResult())));
    }
}
