package com.cities.config;

import com.cities.model.city.City;
import com.cities.model.country.Country;
import com.cities.model.twitter.TwitterSearchModel;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    private static final String GROUP_ID_COUNTRY = "country";
    private static final String GROUP_ID_CITY = "city";

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    private ConsumerFactory<String, Country> countryConsumerFactory() {
        Map<String, Object> props = getConsumerConfigProps(bootstrapAddress, GROUP_ID_COUNTRY);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(Country.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Country> countryKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Country> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(countryConsumerFactory());
        return factory;
    }

    private ConsumerFactory<String, City> cityConsumerFactory() {
        Map<String, Object> props = getConsumerConfigProps(bootstrapAddress, GROUP_ID_CITY);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(City.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, City> cityKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, City> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(cityConsumerFactory());
        return factory;
    }

    private ConsumerFactory<String, TwitterSearchModel> twitterSearchConsumerFactory() {
        Map<String, Object> props = getConsumerConfigProps(bootstrapAddress, GROUP_ID_CITY);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(TwitterSearchModel.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TwitterSearchModel> twitterSearchKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, TwitterSearchModel> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(twitterSearchConsumerFactory());
        return factory;
    }

    private Map<String, Object> getConsumerConfigProps(String bootstrapAddress, String groupIdCountry) {
        Map<String, Object> props = new HashMap<>();
        props.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(GROUP_ID_CONFIG, groupIdCountry);
        props.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return props;
    }
}