package com.cities.model.comment;

import lombok.Data;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="comment")
@Data
public class Comment {

    @Id
    @Column(name="co_id")
    @GeneratedValue(strategy= IDENTITY)
    private Integer id;

    @Column(name="co_text", nullable = false)
    private String text;

    @Column(name="co_user_id", nullable = false)
    private Integer userId;

    @Column(name="co_create_time", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime createdTime;
}
