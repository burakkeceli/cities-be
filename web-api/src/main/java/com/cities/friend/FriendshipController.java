package com.cities.friend;


import com.cities.service.FriendshipService;
import com.cities.user.model.UserDto;
import com.cities.user.model.UserPropertiesView;
import com.cities.validation.FriendshipValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.cities.constant.ApiConstants.Urls.USER_FRIENDSHIP;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = USER_FRIENDSHIP, produces = APPLICATION_JSON_UTF8_VALUE)
@PreAuthorize("hasAnyRole('ROLE_USER')")
public class FriendshipController {

    static Logger log = Logger.getLogger(FriendshipController.class);

    @Autowired
    private FriendshipLogic friendshipLogic;
    @Autowired
    private FriendshipValidator friendshipValidator;
    @Autowired
    private FriendshipService friendshipService;

    @RequestMapping(value = "/pending", method = GET)
    public ResponseEntity getFriendRequets(@AuthenticationPrincipal UserDto userDto) throws JsonProcessingException {
        List<UserDto> userDtoList = friendshipLogic.getPendingRequestUserList(userDto.getId());
        return new ResponseEntity<>(userDtoList, OK);
    }

    @RequestMapping(method = POST)
    public ResponseEntity processFriendshipRequest(@AuthenticationPrincipal UserDto userDto,
                                                   @RequestBody UserPropertiesView userPropertiesView,
                                                   @RequestParam("accept") boolean accept) throws JsonProcessingException {
        if (friendshipValidator.hasUserFriend(userDto.getId(), userPropertiesView.getId())) {
            return new ResponseEntity<>(FORBIDDEN);
        }
        if (!friendshipValidator.hasFriendshipRequest(userDto.getId(), userPropertiesView.getId())) {
            return new ResponseEntity<>(FORBIDDEN);
        }
        friendshipService.processFriendshipRequest(userDto.getId(), userPropertiesView.getId(), accept);
        return new ResponseEntity<>(OK);
    }

    @RequestMapping(value = "/request", method = POST)
    public ResponseEntity sendFriendshipRequest(@AuthenticationPrincipal UserDto userDto,
                                                @RequestBody UserPropertiesView userPropertiesView) throws JsonProcessingException {
        if (friendshipValidator.hasUserFriend(userDto.getId(), userPropertiesView.getId())) {
            return new ResponseEntity<>(FORBIDDEN);
        }
        if (friendshipService.isUserBlocked(userPropertiesView.getId(), userDto.getId())) {
            return new ResponseEntity<>(FORBIDDEN);
        }
        friendshipService.savePendingRequest(userDto.getId(), userPropertiesView.getId());
        return new ResponseEntity<>(OK);
    }
}
