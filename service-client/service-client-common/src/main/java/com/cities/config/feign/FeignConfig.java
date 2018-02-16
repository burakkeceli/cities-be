package com.cities.config.feign;

import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static feign.Logger.Level.FULL;

@Configuration
public class FeignConfig {

    @Bean
    public Feign.Builder feignBuilder(FeignErrorDecoder errorDecoder) {
        return Feign.builder()
                .logger(new FeignLogger())
                .decoder(new JacksonDecoder())
                .encoder(new JacksonEncoder())
                .errorDecoder(errorDecoder)
                .logLevel(FULL);
    }
}
