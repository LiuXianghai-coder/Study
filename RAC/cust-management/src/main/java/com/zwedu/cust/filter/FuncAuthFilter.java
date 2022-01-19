package com.zwedu.cust.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class FuncAuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 看看这个路径权限里有没有维护，如果没有维护，一律放行(也可以改为一律不放行)
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            // 没有登录的话，跳转到登录页
            WebUtils.issueRedirect(request, response, "/login.html");
            return;
        }
        String requestURI = ((HttpServletRequest) request).getServletPath();
        if (!subject.isPermitted(requestURI)) {
            WebUtils.issueRedirect(request, response, "/login.html");
            return;
        }
        chain.doFilter(request, response);
    }
}