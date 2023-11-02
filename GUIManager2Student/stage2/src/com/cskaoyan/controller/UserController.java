package com.cskaoyan.controller;

import com.cskaoyan.model.User;
import com.cskaoyan.service.UserService;
import com.cskaoyan.service.impl.UserServiceImpl;

/**
 * view层请求controller层
 * 该类负责对view层的和User管理员操作相关的请求进行处理
 * 需要将请求分发给service层来进行业务逻辑处理
 *
 * @since 20:18
 * @author wuguidong@com.com.cskaoyan.onaliyun.com
 */
public class UserController {

    // 依赖service层进行业务逻辑处理
    private UserService userService = new UserServiceImpl();

    /**
     * 判断能否登陆，返回一个int状态值
     * 其中：
     * 0,表示正常成功登陆
     * 1,表示用户不存在
     * 2,表示密码输入错误
     * @since 20:26
     * @param user 用户输入用户对象
     * @return int 登陆状态判断
     * @author wuguidong@com.com.cskaoyan.onaliyun.com
     */
    public int judgeLogin(User user) {
        // 根据输入的用户名,查找用户名是否存在
        String usernameInput = user.getUsername();
        if (!userService.checkUserExist(usernameInput)) {
            // service层返回false,表示用户不存在
            return 1;
        }

        // 在用户名已存在的前提下,校验密码是否正确
        if (!userService.checkPwdUsernameExists(user)) {
            // service层返回false,表示密码错误
            return 2;
        }
        // 用户名密码皆正确,登录成功
        return 0;
    }
}
