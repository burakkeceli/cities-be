package com.cities.constant;

public interface ApiConstants {
    interface Urls {
        String USER = "/user";
        String FRIENDSHIP = "/friendship";
        String USER_FRIENDSHIP = USER + FRIENDSHIP;
        String PENDING = USER_FRIENDSHIP + "/pending";
        String LOGIN = "/login";
        String REGISTER = "/register";
        String CITY = "/city";
        String COUNTRY = "/country";
        String LIKED = "/liked";
    }
}
