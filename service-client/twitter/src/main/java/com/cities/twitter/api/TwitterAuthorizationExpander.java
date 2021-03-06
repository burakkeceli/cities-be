package com.cities.twitter.api;

import com.cities.model.twitter.TwitterAuthenticationModel;
import feign.Param;

public class TwitterAuthorizationExpander implements Param.Expander {

    @Override
    public String expand(Object o) {
        TwitterAuthenticationModel authenticationModel = (TwitterAuthenticationModel) o;
        return authenticationModel.getTokenType() + " " + authenticationModel.getAccessToken();
    }
}
