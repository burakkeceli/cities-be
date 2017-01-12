package com.cities.base;


import com.cities.config.SpringMvcConfig;
import com.cities.model.user.User;
import com.cities.user.model.UserDto;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = SpringMvcConfig.class, loader = AnnotationConfigWebContextLoader.class)
@Transactional
public abstract class AbstractBaseControllerITest {

    @Autowired
    private WebApplicationContext wac;

    public MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                 .webAppContextSetup(wac)
                 .apply(springSecurity())
                 .build();
    }

    public void buildRequest(MockHttpServletRequestBuilder request) {
        request.contentType(APPLICATION_JSON_UTF8_VALUE);
        request.accept(APPLICATION_JSON, TEXT_PLAIN, ALL);
    }

    public UserDto getUserToRequest(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        List<GrantedAuthority> authorities = user.getUserRoles().stream().map(userRole -> new SimpleGrantedAuthority(userRole.getRole())).collect(toList());
        userDto.setAuthorities(authorities);
        return userDto;
    }

    public UserDto getUserToRequest() {
        UserDto userDto = new UserDto();
        userDto.setEmail(randomUUID().toString());
        userDto.setUsername(randomUUID().toString());
        //List<GrantedAuthority> authorities = ImmutableList.of(new SimpleGrantedAuthority(ROLE_USER.getName()));
        //userDto.setAuthorities(authorities);
        return userDto;
    }
}
