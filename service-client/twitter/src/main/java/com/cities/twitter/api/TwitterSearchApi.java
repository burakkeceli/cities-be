package com.cities.twitter.api;

import com.cities.twitter.model.TwitterAuthenticationModel;
import com.cities.twitter.model.TwitterSearchModelList;
import feign.Feign;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public interface TwitterSearchApi {

    String BASE_URL = "https://api.twitter.com/1.1/search/tweets.json";

    @RequestLine("GET ?q={query}&lang={lang}")
    @Headers("Authorization: {authorization}")
    TwitterSearchModelList getTwits(@Param(value = "query") String query,
                                    @Param(value = "lang") String language,
                                    @Param(value = "authorization", expander = TwitterAuthorizationExpander.class) TwitterAuthenticationModel authModel);

    @Configuration
    class TwitterSearch {
        @Bean
        TwitterSearchApi twitterSearchApi(Feign.Builder feignBuilder) {
            return feignBuilder.target(TwitterSearchApi.class, BASE_URL);
        }
    }
}
