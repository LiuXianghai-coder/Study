package com.zwedu.cust.controller;

import com.zwedu.cust.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页控制器
 *
 * @author qingchuan
 * @date 2020/12/12
 */
@Slf4j
@Controller
public class IndexController {
    /**
     * index方法
     */
    @RequestMapping("/index")
    public String login(Model model) {
        //用户认证信息
        Subject subject = SecurityUtils.getSubject();
        // 获取登录用户信息
        User user = (User) subject.getPrincipal();
        model.addAttribute("uid", user.getId());
        model.addAttribute("name", user.getUserName());
        return "index";
    }
}
