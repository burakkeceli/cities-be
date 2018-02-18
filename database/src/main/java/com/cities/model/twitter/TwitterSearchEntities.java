package com.cities.model.twitter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TwitterSearchEntities {
    private List<TwitterHashtagModel> hashtags = new ArrayList<>();
}
