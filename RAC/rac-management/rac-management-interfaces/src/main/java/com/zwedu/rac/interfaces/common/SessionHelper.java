package com.zwedu.rac.interfaces.common;

import com.zwedu.rac.application.UserSession;
import com.zwedu.rac.sdk.rdo.UserRdo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * @author qingchuan
 * @date 2020/12/25
 */
public class SessionHelper {
    /**
     * 获取登录用户ID
     *
     * @return 用户ID
     */
    public static Long getLoginUserId() {
        Subject subject = SecurityUtils.getSubject();
        UserSession userSession = (UserSession) subject.getSession().getAttribute("userSession");
        return userSession.getUserRdo().getUserId();
    }

    /**
     * 获取登录业务线ID
     *
     * @return 业务线ID
     */
    public static Long getBizLineId() {
        Subject subject = SecurityUtils.getSubject();
        UserSession userSession = (UserSession) subject.getSession().getAttribute("userSession");
        return userSession.getUserRdo().getBizLineId();
    }


    /**
     * 获取登录业务线ID
     *
     * @return 业务线ID
     */
    public static UserRdo getUserRdo() {
        Subject subject = SecurityUtils.getSubject();
        UserSession userSession = (UserSession) subject.getSession().getAttribute("userSession");
        return userSession.getUserRdo();
    }
}
