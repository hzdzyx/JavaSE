package com.cskaoyan.dao;

import com.cskaoyan.model.User;

/**
 * 这个接口中定义了和User实体类相关的CRUD操作
 * @since 11:28
 * @author wuguidong@cskaoyan.onaliyun.com
 */
public interface UserDao {
    public boolean checkUserExist(String usernameInput);
    public boolean checkPwdUsernameExists(User user);
}
