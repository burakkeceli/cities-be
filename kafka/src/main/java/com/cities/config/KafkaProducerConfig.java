package com.cities.config;

import com.cities.model.city.City;
import com.cities.model.country.Country;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

@Configuration
@Import(BaseConfig.class)
public class KafkaProducerConfig {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    // INFO: Bean creation, cannot be private.
    @Bean
    public ProducerFactory<String, Country> countryProducerFactory() {
        Map<String, Object> configProps = getProducerConfigProps();
        return new DefaultKafkaProducerFactory<>(configProps, null, new JsonSerializer<Country>());
    }

    @Bean
    public KafkaTemplate<String, Country> countryKafkaTemplate() {
        return new KafkaTemplate<>(countryProducerFactory());
    }

    @Bean
    public ProducerFactory<String, City> cityProducerFactory() {
        Map<String, Object> configProps = getProducerConfigProps();
        return new DefaultKafkaProducerFactory<>(configProps, null, new JsonSerializer<City>());
    }

    @Bean
    public KafkaTemplate<String, City> cityKafkaTemplate() {
        return new KafkaTemplate<>(cityProducerFactory());
    }

    private Map<String, Object> getProducerConfigProps() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(RETRIES_CONFIG, 4);
        configProps.put(LINGER_MS_CONFIG, 1);
        configProps.put(BUFFER_MEMORY_CONFIG, 33554432);
        configProps.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return configProps;
    }
}
