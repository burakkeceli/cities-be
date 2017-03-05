package com.cities.comment.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.joda.time.DateTime;

@Data
@EqualsAndHashCode
public class CommentDto {
    private Integer id;
    private String text;
    private DateTime createTime;
}