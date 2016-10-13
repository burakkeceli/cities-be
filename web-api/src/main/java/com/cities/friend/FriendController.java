package com.cities.friend;


import com.cities.model.user.User;
import com.cities.service.FriendshipService;
import com.cities.user.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/user/friend")
@PreAuthorize("hasAnyRole('ROLE_USER')")
public class FriendController {

    static Logger log = Logger.getLogger(FriendController.class.getName());

    @Autowired
    private FriendshipService friendshipService;

    @RequestMapping(value = "request", method = GET)
    public ResponseEntity getFriendRequets(@AuthenticationPrincipal UserDto userDto) throws JsonProcessingException {
        List<User> users = friendshipService.getPendingRequests(userDto.getId());
        return new ResponseEntity<>(users, OK);
    }
}
