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

import static com.cities.ApiConstants.Urls.IS_EXPIRED;
import static com.cities.ApiConstants.Urls.LOGIN;
import static com.cities.AppConstant.tokenHeader;
import static com.cities.model.user.UserRoleEnum.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthenticationControllerITest extends AbstractBaseControllerITest {

    @Autowired
    private UserService userService;
    @Autowired
    private JacksonService jacksonService;
    @Autowired
    private TokenUtils tokenUtils;

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
                .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        // then
        String jsonResult = mvcResult.getResponse().getContentAsString();
        AuthenticationResponse response = jacksonService.fromJson(jsonResult, AuthenticationResponse.class);
        assertThat(response.getToken()).isNotEmpty();
        assertThat(response.getUsername()).isEqualTo(authenticationRequest.getUsername());
    }

    @Test
    public void shouldNotAuthenticateUserWhenUserNotFound() throws Exception {
        // given
        String username = "Jack";
        String password = "123";

        // and
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setUsername(username);
        authenticationRequest.setPassword(password);

        // when
        MockHttpServletRequestBuilder request = post(LOGIN);
        setCommonRequestPart(request);
        String payload = jacksonService.toJson(authenticationRequest);
        request.content(payload);

        mockMvc.perform(request)
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void shouldReturnNotFoundWhenTokenExpired() throws Exception {
        // given
        String username = "Jack";
        String password = "123";

        // and
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(UUID.randomUUID().toString());

        userService.saveUser(user, ROLE_USER);
        tokenUtils.setTokenExpiration(0);
        String token = tokenUtils.generateToken(user);

        // when
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setUsername(username);
        authenticationRequest.setPassword(password);

        // when
        MockHttpServletRequestBuilder request = get(LOGIN + IS_EXPIRED);
        setCommonRequestPart(request);
        request.header(tokenHeader, token);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void shouldReturnOKWhenTokenNotExpired() throws Exception {
        // given
        String username = "Jack";
        String password = "123";

        // and
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(UUID.randomUUID().toString());

        userService.saveUser(user, ROLE_USER);
        tokenUtils.setTokenExpiration(1);
        String token = tokenUtils.generateToken(user);

        // when
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setUsername(username);
        authenticationRequest.setPassword(password);

        // when
        MockHttpServletRequestBuilder request = get(LOGIN + IS_EXPIRED);
        setCommonRequestPart(request);
        request.header(tokenHeader, token);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
    }
}
