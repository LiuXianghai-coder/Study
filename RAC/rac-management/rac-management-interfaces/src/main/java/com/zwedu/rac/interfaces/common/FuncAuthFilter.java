package com.zwedu.rac.interfaces.common;

import com.zwedu.rac.application.UserSession;
import com.zwedu.rac.application.common.StrategyThreadLocal;
import com.zwedu.rac.domain.common.enums.ResponseCodeEnum;
import com.zwedu.rac.domain.common.util.UrlMatcher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.TreeSet;

/**
 * 功能权限过滤器
 */
@Slf4j
public class FuncAuthFilter implements Filter, AbstractHttpServletRequest {

    private String loginUrl;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 看看这个路径权限里有没有维护，如果没有维护，一律放行(也可以改为一律不放行)
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            log.error("not login or session expired!");
            // 没有登录的话，跳转到登录页
            if (isAjaxRequest((HttpServletRequest) request)) {
                String template = "{\"status\":%s,\"msg\":\"%s\"}";
                printMessage((HttpServletResponse) response, template,
                        ResponseCodeEnum.NO_AUTH.getValue(), ResponseCodeEnum.NO_AUTH.getText());
            } else {
                // 跳转到登陆页
                printMessage((HttpServletResponse) response, TEMPLATE, loginUrl);
            }
            return;
        }
        Session session = SecurityUtils.getSubject().getSession();
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        String contextPath = ((HttpServletRequest) request).getContextPath();
        String requestURI = ((HttpServletRequest) request).getServletPath();
        String path = contextPath + requestURI;
        TreeSet<String> urlSet = new TreeSet<>(userSession.getFuncStrategyMap().keySet());
        String url = UrlMatcher.getInstance().find(urlSet, path);
        if (StringUtils.isEmpty(url)) {
            log.error("url:{} not permitted!", requestURI);
            // 没有登录的话，跳转到登录页
            if (isAjaxRequest((HttpServletRequest) request)) {
                String template = "{\"status\":%s,\"msg\":\"%s\"}";
                printMessage((HttpServletResponse) response, template,
                        ResponseCodeEnum.NO_AUTH.getValue(), ResponseCodeEnum.NO_AUTH.getText());
            } else {
                // 跳转到登陆页
                printMessage((HttpServletResponse) response, TEMPLATE, loginUrl);
            }
            return;
        }

        try {
            StrategyThreadLocal.CURRENT_STRATEGY.set(userSession.getFuncStrategyMap().get(url));
            chain.doFilter(request, response);
        } finally {
            StrategyThreadLocal.CURRENT_STRATEGY.remove();
        }
    }

    public FuncAuthFilter(String loginUrl) {
        this.loginUrl = loginUrl;
    }

}