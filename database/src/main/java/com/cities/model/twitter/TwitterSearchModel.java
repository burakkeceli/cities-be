package com.cities.model.twitter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TwitterSearchModel {
    private TwitterSearchModelList searchResult;
    private Integer userId;
    private DateTime searchTime;
}
