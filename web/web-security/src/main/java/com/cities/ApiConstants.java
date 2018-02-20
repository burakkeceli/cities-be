package com.cities;

public interface ApiConstants {
    interface Urls {
        String ADMIN = "/admin";
        String USER = "/user";
        String FRIENDSHIP = "/friendship";
        String USER_FRIENDSHIP = USER + FRIENDSHIP;
        String PENDING = USER_FRIENDSHIP + "/pending";
        String LOGIN = "/login";
        String REGISTER = "/register";
        String CITY = "/city";
        String COUNTRY = "/country";
        String LIKED = "/liked";
        String COMMENT = "/comment";
        String IS_EXPIRED = "/isExpired";
        String TWITTER = "/twitter";
        String SEARCH = "/search";
        String GET_SEARCH_RESULT = "/get-search-result";
    }
}
