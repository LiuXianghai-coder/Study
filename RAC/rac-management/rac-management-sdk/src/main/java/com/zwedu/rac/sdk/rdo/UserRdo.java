package com.zwedu.rac.sdk.rdo;

import java.io.Serializable;
import java.util.Map;

/**
 * 用户信息
 *
 * @author qingchuan
 * @date 2020/12/18
 */
public class UserRdo implements Serializable {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户登录名
     */
    private String userName;
    /**
     * 当前登录业务线ID
     */
    private Long bizLineId;
    /**
     * 用户扩展属性数据
     */
    private Map<String, Object> extData;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getBizLineId() {
        return bizLineId;
    }

    public void setBizLineId(Long bizLineId) {
        this.bizLineId = bizLineId;
    }

    public Map<String, Object> getExtData() {
        return extData;
    }

    public void setExtData(Map<String, Object> extData) {
        this.extData = extData;
    }
}
