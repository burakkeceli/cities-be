package com.cities.service.comment;

import com.cities.dao.CommentDAO;
import com.cities.model.comment.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.joda.time.DateTime.now;

@Service
@Transactional
public class RelationalCommentService {

    @Autowired
    private CommentDAO commentDAO;

    public void saveComment(Comment comment) {
        comment.setCreatedTime(now());
        commentDAO.save(comment);
    }

    public Comment getCommentById(Integer commentId) {
        return commentDAO.getById(commentId);
    }
}
