package com.zwedu.rac.interfaces.controller;

import com.zwedu.rac.application.UserSession;
import com.zwedu.rac.application.service.UserSessionAppService;
import com.zwedu.rac.domain.common.util.BaseResponse;
import com.zwedu.rac.domain.common.util.ResponseUtil;
import com.zwedu.rac.interfaces.common.AbstractHttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录
 *
 * @author qingchuan
 * @date 2020/12/25
 */
@Slf4j
@Controller
public class LoginController implements AbstractHttpServletRequest {

    @Value("${server.url.login}")
    private String loginUrl;
    @Value("${server.url.success}")
    private String successUrl;

    @Resource
    private UserSessionAppService userSessionAppService;

    /**
     * 登录方法
     */
    @RequestMapping("/login")
    public void login(String userName, String password, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        //证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                userName,
                password);
        try {
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(usernamePasswordToken);
            UserSession userSession = userSessionAppService.getSession(BIZ_LINE_ID, userName);
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute("userSession", userSession);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            // 跳转到登陆页
            printMessage(response, TEMPLATE, loginUrl);
            return;
        }
        // 跳转到登陆页
        printMessage(response, TEMPLATE, successUrl);
    }


    /**
     * 登出方法
     */
    @RequestMapping("/logout")
    @ResponseBody
    public BaseResponse logout() {
        //用户认证信息
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        return ResponseUtil.success();
    }


}
