package com.cities.twitter.service;

import com.cities.twitter.api.TwitterSearchApi;
import com.cities.twitter.model.TwitterAuthenticationModel;
import com.cities.twitter.model.TwitterSearchModelList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TwitterSearchService {

    @Autowired
    private TwitterSearchApi twitterSearchApi;

    public TwitterSearchModelList getSearchResult(String query, String queryLanguage, TwitterAuthenticationModel authModel) {
        return twitterSearchApi.getTwits(query, queryLanguage, authModel);
    }

}
