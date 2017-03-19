package com.cities.service.comment;

import com.cities.model.comment.Comment;
import com.cities.model.user.User;
import com.cities.service.comment.model.CassandraCommentModel;
import com.cities.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentService {

    @Autowired
    private CassandraCommentService cassandraCommentService;
    @Autowired
    private RelationalCommentService relationalCommentService;
    @Autowired
    private UserService userService;

    public void saveComment(Integer userId, Integer cityId, String commentText) {
        User user = userService.getUserById(userId);
        Comment comment = createComment(userId, commentText);
        relationalCommentService.saveComment(comment);
        cassandraCommentService.saveCommentOfCity(user, cityId, comment);
    }

    public List<CassandraCommentModel> getCommentsOfCity(Integer cityId) {
        return cassandraCommentService.getCommentsOfCity(cityId);
    }

    private Comment createComment(Integer userId, String commentText) {
        Comment comment = new Comment();
        comment.setText(commentText);
        comment.setUserId(userId);
        return comment;
    }
}
