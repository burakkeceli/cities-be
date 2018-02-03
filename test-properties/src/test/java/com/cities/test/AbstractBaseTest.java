package com.cities.test;

import com.cities.config.BaseConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BaseConfig.class, loader = AnnotationConfigContextLoader.class)
@Transactional
@TestPropertySource("classpath:/test.properties")
public abstract class AbstractBaseTest {
}
