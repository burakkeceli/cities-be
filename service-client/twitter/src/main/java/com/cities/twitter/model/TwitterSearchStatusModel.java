package com.cities.twitter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TwitterSearchStatusModel {

    @JsonProperty("created_at")
    private String createdAt;
    private Long id;
    private String text;
    private TwitterSearchEntities entities;
}
