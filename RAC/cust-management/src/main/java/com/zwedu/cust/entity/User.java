package com.zwedu.cust.entity;

import com.zwedu.rac.sdk.rdo.FuncRdo;

import java.util.List;

/**
 * 用户
 *
 * @author qingchuan
 * @date 2020/12/12
 */
public class User {
    /**
     * id
     */
    private Long id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 功能集合
     */
    private List<FuncRdo> funcRdoList;

    public User(Long id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<FuncRdo> getFuncRdoList() {
        return funcRdoList;
    }

    public void setFuncRdoList(List<FuncRdo> funcRdoList) {
        this.funcRdoList = funcRdoList;
    }
}