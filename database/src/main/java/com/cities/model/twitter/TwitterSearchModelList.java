package com.cities.model.twitter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TwitterSearchModelList {

    @JsonProperty("statuses")
    private List<TwitterSearchStatusModel> twitterSearchStatusModelList = new ArrayList<>();

    @JsonProperty("search_metadata")
    private TwitterSearchMetadata searchMetadata;
}
