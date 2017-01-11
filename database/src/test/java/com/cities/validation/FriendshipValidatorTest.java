package com.cities.validation;

import com.cities.service.FriendshipService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FriendshipValidatorTest {

    @Mock
    private FriendshipService friendshipService;
    @InjectMocks
    private FriendshipValidator friendshipValidator;

    @Test
    public void shouldReturnTrueWhenUserHasUser() {
        // given
            Integer userFromId = 1;
            Integer userToId = 2;
            when(friendshipService.doesUserHaveFriend(userFromId, userToId)).thenReturn(true);

        // when
            boolean result = friendshipValidator.hasUserFriend(userFromId, userToId);

        // then
            assertThat(result).isTrue();
            verify(friendshipService).doesUserHaveFriend(userFromId, userToId);
            verifyNoMoreInteractions(friendshipService);
    }
}
