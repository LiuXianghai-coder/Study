package com.zwedu.cust.service;

import com.zwedu.cust.entity.User;
import org.springframework.stereotype.Service;

/**
 * 用户服务
 *
 * @author qingchuan
 * @date 2020/12/12
 */
@Service
public class UserService {

    public User getUserByName(String userName) {
        return getMapByName(userName);
    }

    /**
     * 模拟数据库查询
     *
     * @param userName 用户名
     * @return User
     */
    private User getMapByName(String userName) {
        User user = new User(4L, "sale1", "123456");
        return user;
    }
}