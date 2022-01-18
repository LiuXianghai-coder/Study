package org.xhliu.shirodemo.entity;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MineRealm extends AuthorizingRealm {
    private final Logger log = LoggerFactory.getLogger(MineRealm.class);

    @Override
    protected AuthorizationInfo
    doGetAuthorizationInfo(
            PrincipalCollection principals
    ) {
        return null;
    }

    @Override
    protected AuthenticationInfo
    doGetAuthenticationInfo(
            AuthenticationToken token
    ) throws AuthenticationException {
        String principal = (String) token.getPrincipal();
        if ("xhliu".equals(principal))
            return new SimpleAuthenticationInfo(principal, "12345678", getName());

        return null;
    }
}
