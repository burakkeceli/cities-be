package com.cities.user;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
@PreAuthorize("hasAnyRole('ROLE_USER')")
public class UserController {

}
