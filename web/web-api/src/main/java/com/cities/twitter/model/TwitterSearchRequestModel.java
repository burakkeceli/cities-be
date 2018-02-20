package com.cities.twitter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TwitterSearchRequestModel {
    private String queryText;
    private String queryLang;
}
