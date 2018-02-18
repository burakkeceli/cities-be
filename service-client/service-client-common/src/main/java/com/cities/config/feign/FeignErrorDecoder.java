package com.cities.config.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

    // TODO: create error decoder
    @Override
    public Exception decode(String s, Response response) {
        return null;
    }

}
