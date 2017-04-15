package com.cities.comment.model;

import lombok.Data;
import org.joda.time.DateTime;

@Data
public class CommentDto {
    private Integer id;
    private String text;
    private String createdTime;
}