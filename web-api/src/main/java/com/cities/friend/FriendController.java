package com.cities.friend;


import com.cities.model.user.User;
import com.cities.service.FriendshipService;
import com.cities.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

    @Autowired
    private FriendshipService friendshipService;

    @RequestMapping(value = "request", method = GET)
    public ResponseEntity getFriendRequets(@AuthenticationPrincipal Authentication auth) {
        UserDto user = (UserDto) auth.getPrincipal();
        List<User> users = friendshipService.getPendingRequests(user.getId());
        return new ResponseEntity<>(users, OK);
    }
}
