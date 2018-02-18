package com.cities.twitter.service;

import com.cities.model.twitter.TwitterAuthenticationModel;
import com.cities.model.twitter.TwitterSearchModel;
import com.cities.model.twitter.TwitterSearchModelList;
import com.cities.twitter.TwitterSearchProducer;
import com.cities.twitter.api.TwitterSearchApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.joda.time.DateTime.now;

@Component
public class TwitterSearchService {

    // TODO: Add application-only authentication
    // TODO: get from application-only authentication
    private static final String CORRECT_ACCESS_TOKEN = "AAAAAAAAAAAAAAAAAAAAAA%2Fn4QAAAAAAX%2FB%2BCSWhYxha8Y%2FKIf0MFi3naaA%3DsAVqbHfpSjeTlkaQ1siCIZX6oQgQvzimWpX0vsQTHFXt4JYVbn";
    private static final String CORRECT_TOKEN_TYPE = "Bearer";

    @Autowired
    private TwitterSearchApi twitterSearchApi;
    @Autowired
    private TwitterSearchProducer twitterSearchProducer;


    TwitterSearchModelList getSearchResult(String query,
                                           String queryLanguage) {
        return twitterSearchApi.getTwits(query,
                queryLanguage,
                new TwitterAuthenticationModel(CORRECT_TOKEN_TYPE, CORRECT_ACCESS_TOKEN));
    }

    public void publishTwitterSearchModel(Integer userId,
                                          String query,
                                          String queryLanguage) {
        TwitterSearchModelList twits = getSearchResult(query, queryLanguage);
        TwitterSearchModel twitterSearchModel = new TwitterSearchModel(twits, userId);
        twitterSearchProducer.sendTwitterSearchMessage(twitterSearchModel);
    }
}
