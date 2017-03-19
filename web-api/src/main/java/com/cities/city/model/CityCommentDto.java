package com.cities.city.model;

import com.cities.comment.model.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class CityCommentDto {
    private Integer cityId;
    private Integer userId;
    private String userName;
    private CommentDto comment;
}
