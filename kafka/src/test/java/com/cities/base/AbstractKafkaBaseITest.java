package com.cities.base;

import com.cities.config.BaseConfig;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BaseConfig.class, loader = AnnotationConfigContextLoader.class)
@PropertySource("classpath:config.properties")
public abstract class AbstractKafkaBaseITest {
}
