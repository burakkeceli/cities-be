package com.cities.model.twitter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TwitterSearchMetadata {

    private Integer count;
    private String query;
    @JsonProperty("next_results")
    private String nextResultUrl;
}
