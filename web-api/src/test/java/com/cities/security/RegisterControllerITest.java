package com.cities.security;

import com.cities.base.AbstractBaseControllerITest;
import com.cities.helper.JacksonService;
import com.cities.model.user.User;
import com.cities.service.user.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static com.cities.constant.ApiConstants.Urls.REGISTER;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegisterControllerITest extends AbstractBaseControllerITest {

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

        // when
        MockHttpServletRequestBuilder request = post(REGISTER);
        setCommonRequestPart(request);
        request.content(jacksonService.toJson(authenticationRequest));

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        // then
        String jsonResult = mvcResult.getResponse().getContentAsString();
        AuthenticationResponse response = jacksonService.fromJson(jsonResult, AuthenticationResponse.class);
        assertThat(response.getToken()).isNotEmpty();

        User user = userService.getUserByName(response.getUsername());
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo(authenticationRequest.getUsername());
    }
}
