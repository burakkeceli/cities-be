package com.cities.service.comment;

import com.cities.model.comment.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CommentService {

    @Autowired
    private CassandraCommentService cassandraCommentService;
    @Autowired
    private RelationalCommentService relationalCommentService;

    public void saveComment(Integer userId, Integer cityId, String commentText) {
        Comment comment = createComment(userId, commentText);
        relationalCommentService.saveComment(comment);
        cassandraCommentService.saveCommentOfCity(cityId, comment.getId());
    }

    public Comment getCommentByCommentId(Integer commentId) {
        return relationalCommentService.getCommentById(commentId);
    }

    public Map<Integer, Integer> getCommentsOfCity(Integer cityId) {
        return cassandraCommentService.getCommentsOfCity(cityId);
    }

    private Comment createComment(Integer userId, String commentText) {
        Comment comment = new Comment();
        comment.setText(commentText);
        comment.setUserId(userId);
        return comment;
    }
}
