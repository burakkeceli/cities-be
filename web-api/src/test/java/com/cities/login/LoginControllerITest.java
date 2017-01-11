package com.cities.login;

import com.cities.base.AbstractBaseControllerITest;
import com.cities.helper.JacksonService;
import com.cities.model.user.User;
import com.cities.security.AuthenticationRequest;
import com.cities.security.AuthenticationResponse;
import com.cities.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static com.cities.constant.ApiConstants.Urls.LOGIN;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoginControllerITest extends AbstractBaseControllerITest {

    @Autowired
    private JacksonService jacksonService;
    @Autowired
    private UserService userService;

    @Test
    public void shouldCreateNewUserAndReturnToken() throws Exception {
        // given
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setUsername(randomUUID().toString());
        authenticationRequest.setPassword(randomUUID().toString());

        MockHttpServletRequestBuilder request = post(LOGIN);
        request.contentType(APPLICATION_JSON_UTF8_VALUE);
        request.accept(APPLICATION_JSON, TEXT_PLAIN, ALL);
        request.content(jacksonService.toJson(authenticationRequest));

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, equalTo(APPLICATION_JSON_UTF8_VALUE)))
                .andReturn();

        String jsonResult = mvcResult.getResponse().getContentAsString();
        AuthenticationResponse response = jacksonService.fromJson(jsonResult, AuthenticationResponse.class);
        assertThat(response.getToken()).isNotEmpty();

        User user = userService.get(authenticationRequest.getUsername());
        assertThat(user).isNotNull();
    }
}
