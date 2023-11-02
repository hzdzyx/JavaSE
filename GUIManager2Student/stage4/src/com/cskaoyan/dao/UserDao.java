package com.cskaoyan.dao;

import com.cskaoyan.model.User;

/**
 * 这个接口中定义了和User实体类相关的CRUD操作
 * @since 11:28
 * @author wuguidong@cskaoyan.onaliyun.com
 */
public interface UserDao {
    /**
     * 获取所有User对象的数组,排除null元素
     * @return com.cskaoyan.model.User[]
     * @author wuguidong@cskaoyan.onaliyun.com
     * @since 11:34
     */
    User[] getAllRealUser();

    /**
     * 通过一个已经确定存在的用户名获取密码
     * @param username 一个已经确定存在的用户名
     * @return java.lang.String
     * @author wuguidong@cskaoyan.onaliyun.com
     * @since 11:34
     */
    String getPwdByRightUsername(String username);
}
