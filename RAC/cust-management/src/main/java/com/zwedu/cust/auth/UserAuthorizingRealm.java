package com.zwedu.cust.auth;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.zwedu.cust.entity.User;
import com.zwedu.cust.service.UserService;
import com.zwedu.rac.sdk.provider.AuthProvider;
import com.zwedu.rac.sdk.rdo.FuncRdo;
import com.zwedu.rac.sdk.rdo.StrategyRdo;
import com.zwedu.rac.sdk.rpo.FuncAuthRpo;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class UserAuthorizingRealm extends AuthorizingRealm {

    private static final Long BIZ_LINE_ID = 2L;

    @DubboReference
    private AuthProvider authProvider;
    @Resource
    private UserService userService;

    /**
     * 授权验证，获取授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) principalCollection.getPrimaryPrincipal();
        // 权限Set集合
        Set<String> urls = Sets.newHashSet();
        for (FuncRdo funcRdo : user.getFuncRdoList()) {
            urls.addAll(funcRdo.getUrlSet());
        }
        // 返回权限
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(urls);
        return info;
    }

    /**
     * 登录验证，获取身份信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        if (token.getPassword() == null || token.getUsername() == null) {
            return null;
        }
        // 获取用户
        User user = userService.getUserByName(token.getUsername());
        if (user == null) {
            throw new UnknownAccountException("账号或密码不正确");
        }
        // 验证密码
        if (!user.getPassword().equals(new String(token.getPassword()))) {
            throw new UnknownAccountException("账号或密码不正确");
        }
        List<FuncRdo> funcRdoList = getSession(BIZ_LINE_ID, user.getId());
        user.setFuncRdoList(funcRdoList);
        // 此处可以持久化用户的登录信息，这里仅做演示没有连接数据库
        return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
    }


    /**
     * 根据UserId获取session信息
     *
     * @param bizLineId 业务线ID
     * @param userId    用户ID
     * @return session信息
     */
    public List<FuncRdo> getSession(Long bizLineId, Long userId) {
        // 获取功能权限策略
        List<FuncRdo> funcRdoList = authProvider.listFuncAuth(new FuncAuthRpo(bizLineId, userId));
        Map<String, StrategyRdo> funcStrategyMap = Maps.newHashMap();
        for (FuncRdo funcRdo : funcRdoList) {
            for (String url : funcRdo.getUrlSet()) {
                funcStrategyMap.put(url, funcRdo.getStrategyRdo());
            }
        }
        return funcRdoList;
    }
}