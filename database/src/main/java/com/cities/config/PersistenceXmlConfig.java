package com.cities.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan({ "com.*" })
public class PersistenceXmlConfig {

    public PersistenceXmlConfig() {
        super();
    }

}