package com.cities.model.user;

public enum UserRoleEnum{
    ROLE_USER(1, "ROLE_USER"),
    ROLE_ADMIN(2, "ROLE_ADMIN");

    private Integer id;
    private String name;

    UserRoleEnum(Integer id, String name) {
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
