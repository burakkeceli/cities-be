package com.cities.twitter.service;

import com.cities.model.twitter.TwitterAuthenticationModel;
import com.cities.model.twitter.TwitterSearchModel;
import com.cities.model.twitter.TwitterSearchModelList;
import com.cities.twitter.TwitterSearchProducer;
import com.cities.twitter.api.TwitterSearchApi;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.joda.time.DateTime.now;

@Component
public class TwitterSearchService {

    @Autowired
    private TwitterSearchApi twitterSearchApi;
    @Autowired
    private TwitterSearchProducer twitterSearchProducer;

    // TODO: Add application-only authentication

    TwitterSearchModelList getSearchResult(String query,
                                           String queryLanguage,
                                           TwitterAuthenticationModel authModel) {
        return twitterSearchApi.getTwits(query, queryLanguage, authModel);
    }

    public void publishTwitterSearchModel(Integer userId,
                                          String query,
                                          String queryLanguage,
                                          TwitterAuthenticationModel authModel) {
        TwitterSearchModelList twits = twitterSearchApi.getTwits(query, queryLanguage, authModel);
        TwitterSearchModel twitterSearchModel = new TwitterSearchModel(twits, userId, now());
        twitterSearchProducer.sendTwitterSearchMessage(twitterSearchModel);
    }

}
