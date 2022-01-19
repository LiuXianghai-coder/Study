package com.zwedu.cust.auth;

import com.google.common.collect.Maps;
import com.zwedu.cust.filter.FuncAuthFilter;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置
 *
 * @author qingchuan
 * @date 2020/12/12
 */
@Configuration
public class ShiroConfig {

    @Bean(name = "authorizingRealm")
    public UserAuthorizingRealm authorizingRealm() {
        UserAuthorizingRealm userAuthorizingRealm = new UserAuthorizingRealm();
        return userAuthorizingRealm;
    }

    //权限管理，配置主要是Realm的管理认证
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(authorizingRealm());
        return securityManager;
    }

    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        // 登出匹配
        map.put("/logout", "logout");
        // 登录匹配
        map.put("/login.html", "anon");
        map.put("/login", "anon");
        // 其他请求，功能授权拦截
        map.put("/**", "funcAuth");
        // 登录页面
        shiroFilterFactoryBean.setLoginUrl("/login.html");
        // 首页
        shiroFilterFactoryBean.setSuccessUrl("/index.html");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        Map<String, Filter> filterMap = Maps.newHashMap();
        filterMap.put("logout", new LogoutFilter());
        filterMap.put("anon", new AnonymousFilter());
        filterMap.put("funcAuth", new FuncAuthFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        return shiroFilterFactoryBean;
    }
}
