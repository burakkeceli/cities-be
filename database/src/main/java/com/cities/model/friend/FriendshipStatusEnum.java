package com.cities.model.friend;

public enum FriendshipStatusEnum {
    ACTIVE(1, "ACTIVE"),
    BLOCKED(2, "BLOCKED"),
    PENDING(3, "PENDING");

    private Integer id;
    private String name;

    FriendshipStatusEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
