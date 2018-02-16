package com.cities.twitter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TwitterSearchModelList {

    @JsonProperty("statuses")
    private List<TwitterSearchStatusModel> twitterSearchStatusModelList = new ArrayList<>();

    @JsonProperty("search_metadata")
    private TwitterSearchMetadata searchMetadata;
}
