package com.zwedu.rac.sdk.rpo;

import java.io.Serializable;

/**
 * 菜单查询请求参数对象
 *
 * @author qingchuan
 * @date 2020/12/11
 */
public class MenuRpo implements Serializable {
    /**
     * 业务线ID
     */
    private Long bizLineId;
    /**
     * 用户ID
     */
    private Long userId;

    public static MenuRpo of(Long userId, Long bizLineId) {
        MenuRpo menuRpo = new MenuRpo();
        menuRpo.setUserId(userId);
        menuRpo.setBizLineId(bizLineId);
        return menuRpo;
    }

    public Long getBizLineId() {
        return bizLineId;
    }

    public void setBizLineId(Long bizLineId) {
        this.bizLineId = bizLineId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
