package com.cskaoyan.service;

import com.cskaoyan.model.User;

/**
 * 这个接口中定义了与User相关业务逻辑操作
 * @since 11:29
 * @author wuguidong@cskaoyan.onaliyun.com
 */
public interface UserService {
    /**
     * 先调用dao层获取装有所有的User对象的数组(数组中不包含null元素)
     * 然后逐一比较用户名进行校验
     * 该方法返回true表示用户名存在,否则表示不存在
     *
     * @param usernameInput 被校验的用户名
     * @return boolean
     * @author wuguidong@cskaoyan.onaliyun.com
     * @since 10:29
     */
    boolean checkUserExist(String usernameInput);

    /**
     * 传入的用户输入的User对象当中的username是必然存在的
     * 将此username传给dao层先获取正确的密码,然后比较密码
     * 该方法返回true表示密码正确,否则表示错误
     *
     * @param user 用户输入的User对象
     * @return boolean
     * @author wuguidong@cskaoyan.onaliyun.com
     * @since 10:30
     */
    boolean checkPwdUsernameExists(User user);
}
