package com.cities.friend;


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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/user/friend")
@PreAuthorize("hasAnyRole('ROLE_USER')")
public class FriendshipController {

    static Logger log = Logger.getLogger(FriendshipController.class);

    @Autowired
    private FriendshipLogic friendshipLogic;
    @Autowired
    private FriendshipValidator friendshipValidator;

    @RequestMapping(value = "request", method = GET)
    public ResponseEntity getFriendRequets(@AuthenticationPrincipal UserDto userDto) throws JsonProcessingException {
        List<UserDto> userDtoList = friendshipLogic.getPendingRequestUserList(userDto.getId());
        return new ResponseEntity<>(userDtoList, OK);
    }

    @RequestMapping(value = "accept", method = POST)
    public ResponseEntity acceptFriendshipRequest(@AuthenticationPrincipal UserDto userDto,
                                                  @RequestBody UserPropertiesView userPropertiesView) throws JsonProcessingException {
        if (friendshipValidator.validateUserHasFriend(userDto.getId(), userPropertiesView.getId())) {
            return new ResponseEntity<>(FORBIDDEN);
        }
        if (!friendshipValidator.hasFriendshipRequest(userDto.getId(), userPropertiesView.getId())) {
            return new ResponseEntity<>(FORBIDDEN);
        }
        friendshipLogic.acceptFriendshipRequest(userDto.getId(), userPropertiesView.getId());
        return new ResponseEntity<>(OK);
    }
}
