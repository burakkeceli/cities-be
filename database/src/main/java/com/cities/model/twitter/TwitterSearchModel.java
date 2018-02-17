package com.cities.model.twitter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TwitterSearchModel {
    private TwitterSearchModelList searchResult;
    private Integer userId;
}
