package org.xhliu.shirodemo.config;

import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xhliu.shirodemo.entity.MineRealm;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
    public Realm realm() {
        return new MineRealm();
    }

    @Bean
    public DefaultSecurityManager securityManager(Realm realm) {
        DefaultSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean
    shiroFilterFactoryBean(
            SecurityManager manager
    ) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(manager);
        Map<String, String> map = new HashMap<>();

        map.put("/logout", "anon"); // 登出匹配
        map.put("/login", "anon"); // 登录匹配
        map.put("/**", "authc"); // 对于其它的请求路径，需要授权

        factoryBean.setLoginUrl("/login"); // 登录界面
        factoryBean.setSuccessUrl("/index"); // 首页
        factoryBean.setFilterChainDefinitionMap(map);

        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("anon", new AnonymousFilter());
        filterMap.put("authc", new AnonymousFilter());

        factoryBean.setFilters(filterMap);

        return factoryBean;
    }
}
