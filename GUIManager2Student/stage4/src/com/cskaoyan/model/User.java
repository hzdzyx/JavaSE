package com.cskaoyan.model;

/**
 * 管理员用户实体类
 *
 * @since 10:01
 * @author wuguidong@com.com.cskaoyan.onaliyun.com
 */
public class User {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
