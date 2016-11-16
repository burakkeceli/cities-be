package com.cities.model.friend;

import com.cities.model.user.User;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static org.hibernate.annotations.CascadeType.PERSIST;


@Entity
@Table(name = "friendship",
        uniqueConstraints = @UniqueConstraint(columnNames={"user_from_id", "user_to_id"}))
@Data
public class Friendship {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy= IDENTITY)
    private Integer id;

    /*@Column
    @Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
    private DateTime time = DateTime.now();*/

    @ManyToOne(fetch = LAZY)
    @Cascade({PERSIST})
    @JoinColumn(name = "user_from_id")
    private User userFrom;

    @ManyToOne(fetch = LAZY)
    @Cascade({PERSIST})
    @JoinColumn(name = "user_to_id")
    private User userTo;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private FriendshipStatusEnum friendshipStatusEnum;
}
