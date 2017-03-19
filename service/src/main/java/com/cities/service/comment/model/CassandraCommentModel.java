package com.cities.service.comment.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CassandraCommentModel {
    private Integer cityId;
    private Integer commentId;
    private String commentText;
    private Integer userId;
    private String userName;
    private UUID createdTime;
}
