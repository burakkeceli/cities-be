package com.cities.dao;

import com.cities.base.AbstractBaseITest;
import com.cities.model.comment.Comment;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentDAOITest extends AbstractBaseITest {

    @Autowired
    private CommentDAO commentDAO;

    @Test
    public void shouldSaveComment() {

        // given
        String text = UUID.randomUUID().toString();
        DateTime createTime = DateTime.now();

        // and
        Comment comment = new Comment();
        comment.setText(text);
        comment.setUserId(1);
        comment.setCreatedTime(createTime);

        // when
        commentDAO.save(comment);

        // then
        Comment fetchedComment = commentDAO.getById(comment.getId());
        assertThat(fetchedComment.getText()).isEqualTo(text);
        assertThat(fetchedComment.getUserId()).isEqualTo(1);
        assertThat(fetchedComment.getCreatedTime()).isEqualTo(createTime);
    }
}
