package com.cities.security;

import com.cities.base.AbstractBaseControllerITest;
import com.cities.helper.JacksonService;
import com.cities.model.user.User;
import com.cities.service.user.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.UUID;

import static com.cities.constant.ApiConstants.Urls.LOGIN;
import static com.cities.model.user.UserRoleEnum.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthenticationControllerITest extends AbstractBaseControllerITest {

    @Autowired
    private UserService userService;
    @Autowired
    private JacksonService jacksonService;

    @Test
    public void shouldAuthenticateUser() throws Exception {
        // given
        String username = "Jack";
        String password = "123";

        // and
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(UUID.randomUUID().toString());

        userService.saveUser(user, ROLE_USER);

        // and
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setUsername(username);
        authenticationRequest.setPassword(password);

        // when
        MockHttpServletRequestBuilder request = post(LOGIN);
        setCommonRequestPart(request);
        String payload = jacksonService.toJson(authenticationRequest);
        request.content(payload);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, equalTo(APPLICATION_JSON_UTF8_VALUE)))
                .andReturn();

        // then
        String jsonResult = mvcResult.getResponse().getContentAsString();
        AuthenticationResponse response = jacksonService.fromJson(jsonResult, AuthenticationResponse.class);
        assertThat(response.getToken()).isNotEmpty();
        assertThat(response.getUsername()).isEqualTo(authenticationRequest.getUsername());
    }
}
