package com.cities.twitter.service;

import com.cities.test.AbstractBaseTest;
import com.cities.twitter.model.TwitterAuthenticationModel;
import com.cities.twitter.model.TwitterSearchModelList;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class TwitterSearchServiceMTest extends AbstractBaseTest {

    private static final String CORRECT_ACCESS_TOKEN = "AAAAAAAAAAAAAAAAAAAAAA%2Fn4QAAAAAAX%2FB%2BCSWhYxha8Y%2FKIf0MFi3naaA%3DsAVqbHfpSjeTlkaQ1siCIZX6oQgQvzimWpX0vsQTHFXt4JYVbn";
    private static final String CORRECT_TOKEN_TYPE = "Bearer";

    @Autowired
    private TwitterSearchService twitterSearchService;

    @Test
    public void shouldGetSearchList() {
        // when
        TwitterSearchModelList searchResult = twitterSearchService.getSearchResult("berlin", "en", new TwitterAuthenticationModel(CORRECT_TOKEN_TYPE, CORRECT_ACCESS_TOKEN));

        // then
        assertThat(searchResult.getTwitterSearchStatusModelList()).isNotEmpty();
    }

}
