package com.cities.twitter;

import com.cities.model.twitter.TwitterSearchModel;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TwitterSearchListener {

    @KafkaListener(
            topics = "${twitter.search.topic.name}",
            containerFactory = "twitterSearchKafkaListenerContainerFactory")
    public void twitterSearchListener(TwitterSearchModel twitterSearchModel) {
    }
}
