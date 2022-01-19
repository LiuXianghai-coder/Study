package com.zwedu.rac.interfaces.common;

import com.zwedu.rac.application.dto.user.UserSimpleDto;
import com.zwedu.rac.application.service.UserAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

@Slf4j
public class UserAuthorizingRealm extends AuthorizingRealm {

    @Resource
    private UserAppService userAppService;

    /**
     * 授权验证，获取授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("call doGetAuthorizationInfo start...");
        String user = (String) principalCollection.getPrimaryPrincipal();
        // 权限Set集合
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        return info;
    }

    /**
     * 登录验证，获取身份信息
     *
     * @return
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        log.info("call doGetAuthenticationInfo start...");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        if (token.getPassword() == null || token.getUsername() == null) {
            return null;
        }
        // 获取用户
        UserSimpleDto user = userAppService.queryByEnName(token.getUsername());
        if (user == null) {
            throw new UnknownAccountException("账号或密码不正确");
        }
        // 验证密码 @TODO 加盐验证
        if (!user.getPassword().equals(new String(token.getPassword()))) {
            throw new UnknownAccountException("账号或密码不正确");
        }
        // 此处可以持久化用户的登录信息，这里仅做演示没有连接数据库
        return new SimpleAuthenticationInfo(user.getEnName(), user.getPassword(), user.getCnName());
    }
}