package com.mashibing.returnjson;

public class UserInfo {
    private String name;
    private String avatar = "/avatar2.jpg";
    private Permissions role;

    public UserInfo() {

    }

    public UserInfo(String name, Permissions role) {
        this.name = name;
        this.avatar = avatar;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Permissions getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "UserInfo={'" + name + '\'' +
                ",avatar='" + avatar + '\'' +
                ",role=" + role +
                '}';
    }

}
