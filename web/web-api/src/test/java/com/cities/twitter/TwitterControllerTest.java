package com.cities.twitter;

import com.cities.twitter.model.TwitterSearchRequestModel;
import com.cities.twitter.service.TwitterSearchService;
import com.cities.user.model.UserDto;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import static com.cities.model.user.UserRoleEnum.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

@RunWith(MockitoJUnitRunner.class)
public class TwitterControllerTest {

    @Mock
    private TwitterSearchService twitterSearchService;

    @InjectMocks
    private TwitterController twitterController;

    @Test
    public void shouldPublishTwitters() {
        // given
        UserDto userDto = new UserDto(1, "user", "pass", DateTime.now(), createAuthorityList(ROLE_USER.getName()));
        TwitterSearchRequestModel twitterSearchRequestModel = new TwitterSearchRequestModel("berlin", "en");

        // when
        ResponseEntity responseEntity = twitterController.searchTwitters(twitterSearchRequestModel, userDto);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
    }
}
