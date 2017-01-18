package com.cities.service.validation;

import com.cities.service.friendship.FriendshipService;
import com.cities.service.friendship.validation.FriendshipValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

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
            Mockito.verify(friendshipService).doesUserHaveFriend(userFromId, userToId);
            verifyNoMoreInteractions(friendshipService);
    }
}
