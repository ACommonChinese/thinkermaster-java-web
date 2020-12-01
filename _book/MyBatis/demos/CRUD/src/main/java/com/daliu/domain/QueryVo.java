package com.daliu.domain;

public class QueryVo {
    private User user;

    public String getNameContainsDragon() {
        return "%龙%";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

