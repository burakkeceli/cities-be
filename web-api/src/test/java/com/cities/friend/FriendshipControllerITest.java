package com.cities.friend;

import com.cities.base.AbstractBaseControllerITest;
import com.cities.helper.BaseTestHelper;
import com.cities.model.user.User;
import com.cities.service.FriendshipService;
import com.cities.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;

import static com.cities.constant.ApiConstants.Urls.USER_FRIENDSHIP;
import static java.util.UUID.randomUUID;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FriendshipControllerITest extends AbstractBaseControllerITest {

    @Autowired
    private FriendshipService friendshipService;
    @Autowired
    private UserService userService;
    @Autowired
    private BaseTestHelper baseTestHelper;

    @Test
    public void shouldGetFriendshipRequests() throws Exception {
        // given
        User userFrom = baseTestHelper.createUserWithUserRole(randomUUID().toString(), randomUUID().toString());
        User userTo = baseTestHelper.createUserWithUserRole(randomUUID().toString(), randomUUID().toString());

        baseTestHelper.createFriendshipRequest(userFrom, userTo);

        MvcResult mvcResult = mockMvc.perform(get(USER_FRIENDSHIP + "/pending").with(user(getUserToRequest(userFrom))))
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, equalTo(APPLICATION_JSON_UTF8_VALUE)))
                .andReturn();

        mvcResult.getResponse();
    }
}
