package com.cskaoyan.service.impl;

import com.cskaoyan.dao.UserDao;
import com.cskaoyan.dao.impl.UserDaoImpl;
import com.cskaoyan.model.User;
import com.cskaoyan.service.UserService;

/**
 * controller层依赖service层完成业务逻辑处理
 * 该类负责对User管理员相关的业务逻辑的处理
 * 该层需要依赖dao层来获取数据然后处理数据,但该层不直接和数据源交互
 *
 * @since 10:18
 * @author wuguidong@com.com.cskaoyan.onaliyun.com
 */
public class UserServiceImpl implements UserService {
    // 需要调用dao层获取数据
    private UserDao userDao = new UserDaoImpl();

    public boolean checkUserExist(String usernameInput) {
        // 先调用dao层获取所有User对象,此数组不包含null元素
        User[] realUsers = userDao.getAllRealUser();
        for (User realUser : realUsers) {
            if (realUser.getUsername().equals(usernameInput)) {
                // 用户名已存在
                return true;
            }
        }
        // 用户名不存在
        return false;
    }

    public boolean checkPwdUsernameExists(User user) {
        String rightPwd = userDao.getPwdByRightUsername(user.getUsername());
        return rightPwd.equals(user.getPassword());
    }
}
