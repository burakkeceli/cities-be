package com.cities.city.model;

import com.cities.comment.model.CommentDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class CityCommentDto {

    private Integer cityId;
    private String cityName;
    private Integer userId;
    private String userName;
    private CommentDto comment;
}
