package com.cities.twitter.service;

import com.cities.test.AbstractBaseTest;
import com.cities.model.twitter.TwitterSearchModelList;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class TwitterSearchServiceMTest extends AbstractBaseTest {

    @Autowired
    private TwitterSearchService twitterSearchService;

    @Test
    public void shouldGetSearchList() {
        // when
        TwitterSearchModelList searchResult = twitterSearchService.getSearchResult("berlin", "en");

        // then
        assertThat(searchResult.getTwitterSearchStatusModelList()).isNotEmpty();
    }

}
