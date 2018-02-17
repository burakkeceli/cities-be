package com.cities.model.twitter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TwitterAuthenticationModel {
    private String tokenType;
    private String accessToken;
}
