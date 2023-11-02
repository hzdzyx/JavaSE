package com.cskaoyan.dao.impl;


import com.cskaoyan.dao.UserDao;
import com.cskaoyan.model.User;
import com.cskaoyan.model.UserData;

/**
 * service层依赖dao层进行数据处理,dao层直接和数据源交互
 * 该类负责和User管理员相关的数据处理
 *
 * 该层仅负责将数据源中的数据提供给service层,该层不做数据校验以及处理
 *
 * @author wuguidong@com.com.cskaoyan.onaliyun.com
 * @since 19:36
 */
public class UserDaoImpl implements UserDao {

    // 从数据源获取数据
    private User[] users = UserData.USERS;

    public User[] getAllRealUser() {
        // 用于记录真实用户个数
        int count = 0;
        for (User user : users) {
            if (user != null) {
                count++;
            }
        }
        User[] result = new User[count];
        int index = 0;
        // 通过数组元素的拷贝实现去掉源数组中的null元素
        for (User user : users) {
            if (user != null) {
                result[index] = user;
            }
            index++;
        }
        return result;
    }

    public String getPwdByRightUsername(String username) {
        User[] realUsers = getAllRealUser();
        String resultPass = null;
        for (User realUser : realUsers) {
            if (realUser.getUsername().equals(username)) {
                resultPass = realUser.getPassword();
            }
        }
        return resultPass;
    }
}
