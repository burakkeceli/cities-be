package com.cities.friend;

import com.cities.user.model.UserDto;
import com.google.common.collect.ImmutableMap;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class UserLogicTest {

    @InjectMocks
    private UserLogic userLogic;

    @Test
    public void shouldReturnUserDtoListWhenUserMapGiven() {
        // given
        Integer userId = 1;
        String userName = UUID.randomUUID().toString();
        Map<Integer, String> userMap = ImmutableMap.of(userId, userName);

        // expected user
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setUsername(userName);

        // when
        List<UserDto> userDtoList = userLogic.fromUserMap(userMap);

        // then
        Assertions.assertThat(userDtoList).hasSize(1);
        Assertions.assertThat(userDtoList).containsExactly(userDto);
    }
}
