package com.cities.twitter;


import com.cities.model.twitter.TwitterSearchModelList;
import com.cities.twitter.model.TwitterSearchRequestModel;
import com.cities.twitter.service.TwitterSearchService;
import com.cities.user.model.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.cities.ApiConstants.Urls.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@RestController
@RequestMapping(value = TWITTER, produces = APPLICATION_JSON_UTF8_VALUE)
public class TwitterController {

    @Autowired
    private TwitterSearchService twitterSearchService;
    @Autowired
    private RedisTwitterService redisTwitterService;

    @RequestMapping(value = SEARCH, method = POST)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity searchTwitters(@RequestBody TwitterSearchRequestModel twitterSearchRequestModel,
                                         @AuthenticationPrincipal UserDto userDto) {
        try {
            twitterSearchService.publishTwitterSearchModel(userDto.getId(),
                    twitterSearchRequestModel.getQueryText(),
                    twitterSearchRequestModel.getQueryLang());
            return new ResponseEntity<>(OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
        }
    }

    @RequestMapping(value = GET_SEARCH_RESULT, method = GET)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity getSearchResult(@AuthenticationPrincipal UserDto userDto) {
        TwitterSearchModelList result = redisTwitterService.getTwitterSearchResultByUserId(userDto.getId());
        return new ResponseEntity<>(result, OK);
    }
}
