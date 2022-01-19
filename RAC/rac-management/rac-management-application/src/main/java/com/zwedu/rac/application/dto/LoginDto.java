package com.zwedu.rac.application.dto;

/**
 * 登录
 *
 * @author qingchuan
 * @date 2020/12/25
 */
public class LoginDto {
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
