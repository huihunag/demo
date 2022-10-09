package com.demo.swaggerknife4jutils.reflex;

import com.demo.swaggerknife4jutils.reflex.service.UserService;

public class UserController {

    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
