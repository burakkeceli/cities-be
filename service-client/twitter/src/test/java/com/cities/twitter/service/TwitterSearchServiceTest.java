package com.cities.twitter.service;

import com.cities.model.twitter.*;
import com.cities.twitter.TwitterSearchProducer;
import com.cities.twitter.api.TwitterSearchApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TwitterSearchServiceTest {

    @Mock
    private TwitterSearchApi twitterSearchApi;
    @Mock
    private TwitterSearchProducer twitterSearchProducer;
    @Captor
    private ArgumentCaptor<TwitterSearchModel> twitterSearchModel;

    @InjectMocks
    private TwitterSearchService twitterSearchService;

    @Test
    public void shouldPublishTwitterSearch() {
        // given
        List<TwitterSearchStatusModel> twitterSearchStatusModelList = new ArrayList<>();
        TwitterSearchMetadata searchMetadata = new TwitterSearchMetadata();

        TwitterSearchModelList modelList = new TwitterSearchModelList(twitterSearchStatusModelList, searchMetadata);

        // and
        Integer userId = 1;
        String query = "Berlin";
        String lang = "Language";

        // and
        when(twitterSearchApi.getTwits(eq(query), eq(lang), any(TwitterAuthenticationModel.class))).thenReturn(modelList);

        // when
        twitterSearchService.publishTwitterSearchModel(userId, query, lang);

        // then
        verify(twitterSearchApi, times(1)).getTwits(eq(query), eq(lang), any(TwitterAuthenticationModel.class));
        verify(twitterSearchProducer, times(1)).sendTwitterSearchMessage(twitterSearchModel.capture());

        // and
        TwitterSearchModel searchModel = twitterSearchModel.getValue();
        assertThat(searchModel.getUserId()).isEqualTo(userId);
        assertThat(searchModel.getSearchResult()).isEqualTo(modelList);
    }
}
