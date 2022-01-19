package com.zwedu.cust.controller;

import com.zwedu.cust.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 登录控制器
 *
 * @author qingchuan
 * @date 2020/12/12
 */
@Slf4j
@Controller
public class LoginController {

    /**
     * 登录页
     */
    @RequestMapping("/login.html")
    public String toLogin(String userName, String password) {
        return "login";
    }

    /**
     * 登录方法
     */
    @RequestMapping("/login")
    public String login(String userName, String password, Model model) {
        //用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                userName,
                password);
        try {
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(usernamePasswordToken);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "login";
        }
        // 获取登录用户信息
        User user = (User) subject.getPrincipal();
        model.addAttribute("uid", user.getId());
        model.addAttribute("name", user.getUserName());
        return "index";
    }
}
