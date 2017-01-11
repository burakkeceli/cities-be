package com.cities.friend;

import com.cities.base.AbstractBaseControllerITest;
import com.cities.helper.BaseTestHelper;
import com.cities.helper.JacksonService;
import com.cities.model.friend.Friendship;
import com.cities.model.friend.FriendshipStatusEnum;
import com.cities.model.user.User;
import com.cities.service.FriendshipService;
import com.cities.service.UserService;
import com.cities.user.model.UserDto;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.cities.constant.ApiConstants.Urls.USER_FRIENDSHIP;
import static com.cities.model.friend.FriendshipStatusEnum.ACTIVE;
import static com.cities.model.friend.FriendshipStatusEnum.REJECTED;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FriendshipControllerITest extends AbstractBaseControllerITest {

    @Autowired
    private FriendshipService friendshipService;
    @Autowired
    private UserService userService;
    @Autowired
    private BaseTestHelper baseTestHelper;
    @Autowired
    private JacksonService jacksonService;

    @Test
    public void shouldGetFriendshipRequests() throws Exception {
        // given
        User userFrom = baseTestHelper.createUserWithUserRole(randomUUID().toString(), randomUUID().toString());
        User userTo = baseTestHelper.createUserWithUserRole(randomUUID().toString(), randomUUID().toString());

        baseTestHelper.createFriendshipRequest(userFrom, userTo);

        // when
        MvcResult mvcResult = mockMvc.perform(get(USER_FRIENDSHIP + "/pending").with(user(getUserToRequest(userTo))))
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, equalTo(APPLICATION_JSON_UTF8_VALUE)))
                .andReturn();

        // then
        String jsonResult = mvcResult.getResponse().getContentAsString();
        List<UserDto> userDtoList = jacksonService.fromJson(jsonResult, new TypeReference<List<UserDto>>() {});
        assertThat(userDtoList).hasSize(1);
        assertThat(userDtoList.get(0).getEmail()).isEqualTo(userFrom.getEmail());
        assertThat(userDtoList.get(0).getUsername()).isEqualTo(userFrom.getUsername());
        assertThat(userDtoList.get(0).getPassword()).isEqualTo(userFrom.getEmail());
    }

    @Test
    public void shouldAcceptFriendshipRequest() throws Exception {
        // given
        User userFrom = baseTestHelper.createUserWithUserRole(randomUUID().toString(), randomUUID().toString());
        User userTo = baseTestHelper.createUserWithUserRole(randomUUID().toString(), randomUUID().toString());

        baseTestHelper.createFriendshipRequest(userFrom, userTo);

        // when
        mockMvc.perform(post(USER_FRIENDSHIP).requestAttr("accept", "true").with(user(getUserToRequest(userTo))))
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, equalTo(APPLICATION_JSON_UTF8_VALUE)))
                .andReturn();

        // then
        Friendship friendship = friendshipService.getFriendship(userFrom.getId(), userTo.getId());
        assertThat(friendship.getFriendshipStatusEnum()).isEqualTo(ACTIVE);
    }

    @Test
    public void shouldRejectFriendshipRequest() throws Exception {
        // given
        User userFrom = baseTestHelper.createUserWithUserRole(randomUUID().toString(), randomUUID().toString());
        User userTo = baseTestHelper.createUserWithUserRole(randomUUID().toString(), randomUUID().toString());

        baseTestHelper.createFriendshipRequest(userFrom, userTo);

        // when
        mockMvc.perform(post(USER_FRIENDSHIP).requestAttr("accept", "false").with(user(getUserToRequest(userTo))))
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, equalTo(APPLICATION_JSON_UTF8_VALUE)))
                .andReturn();

        // then
        Friendship friendship = friendshipService.getFriendship(userFrom.getId(), userTo.getId());
        assertThat(friendship.getFriendshipStatusEnum()).isEqualTo(REJECTED);
    }
}
