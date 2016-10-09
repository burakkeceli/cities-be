package com.cities.model.friend;

import com.cities.model.user.User;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static org.hibernate.annotations.CascadeType.*;


@Entity
@Table(name = "friendship",
        uniqueConstraints = @UniqueConstraint(columnNames={"user_from_id", "user_to_id"}))
public class Friendship {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy= IDENTITY)
    private int id;

    /*@Column
    @Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
    private DateTime time = DateTime.now();*/

    @ManyToOne
    @Cascade({PERSIST})
    @JoinColumn(name = "user_from_id")
    private User userFrom;

    @ManyToOne
    @Cascade({PERSIST})
    @JoinColumn(name = "user_to_id")
    private User userTo;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private FriendshipStatusEnum friendshipStatusEnum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /*public DateTime getTime() {
        return time;
    }

    public void setTime(DateTime time) {
        this.time = time;
    }*/

    public User getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    public User getUserTo() {
        return userTo;
    }

    public void setUserTo(User userTo) {
        this.userTo = userTo;
    }

    public FriendshipStatusEnum getFriendshipStatusEnum() {
        return friendshipStatusEnum;
    }

    public void setFriendshipStatusEnum(FriendshipStatusEnum friendshipStatusEnum) {
        this.friendshipStatusEnum = friendshipStatusEnum;
    }
}
