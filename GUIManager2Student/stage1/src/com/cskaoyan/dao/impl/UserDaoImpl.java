package com.cskaoyan.dao.impl;

import com.cskaoyan.dao.UserDao;
import com.cskaoyan.model.User;
import com.cskaoyan.model.UserData;

import java.util.Objects;

/**
 * service层依赖dao层进行数据处理,dao层直接和数据源交互
 * 该类负责和User管理员相关的数据处理
 *
 * 该层仅负责将数据源中的数据提供给service层,该层不做数据校验以及处理
 * @author wuguidong@com.com.cskaoyan.onaliyun.com
 * @since 19:36
 */
public class UserDaoImpl implements UserDao {
    private static User[] users = UserData.USERS;

    @Override
    public boolean checkUserExist(String usernameInput) {
        for (User user : users) {
            if(user != null){
                if (usernameInput.equals(user.getUsername()))
                    return true;
            }

        }
        return false;
    }

    @Override
    public boolean checkPwdUsernameExists(User user) {
        for(User u : users){
            if(u!=null)
                if(user.getPassword().equals(u.getPassword()))
                    return true;
        }
        return false;
    }
}
