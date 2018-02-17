package com.cities.twitter;

import com.cities.model.twitter.TwitterSearchModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TwitterSearchProducer {

    @Autowired
    private KafkaTemplate<String, TwitterSearchModel> twitterSearchKafkaTemplate;

    @Value(value = "${twitter.search.topic.name}")
    private String twitterSearchTopicName;

    public void sendTwitterSearchMessage(TwitterSearchModel twitterSearchModel) {
        twitterSearchKafkaTemplate.send(twitterSearchTopicName, twitterSearchModel);
    }

}
