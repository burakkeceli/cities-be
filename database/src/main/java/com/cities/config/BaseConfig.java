package com.cities.config;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@PropertySource("classpath:config.properties")
@ComponentScan(basePackages = { "com.*" })
@Configuration
@Import(PersistenceConfig.class)
public class BaseConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
