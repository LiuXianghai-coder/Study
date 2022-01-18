package org.xhliu.shirodemo.example;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncryptRealm extends AuthorizingRealm {
    private final static Logger log = LoggerFactory.getLogger(EncryptRealm.class);

    /**
     * 具体子类实现的权限验证方法
     */
    @Override
    protected AuthorizationInfo
    doGetAuthorizationInfo(
            PrincipalCollection principals
    ) {
        return null;
    }


    /***
     * 由具体子类实现的授权方式，在这里采用了 SHA-256 的方式对原来的密码进行散列
     */
    @Override
    protected AuthenticationInfo
    doGetAuthenticationInfo(
            AuthenticationToken token
    ) throws AuthenticationException {
        String principal = (String) token.getPrincipal();
        if ("xhliu".endsWith(principal)) {
            String password = "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92";
            return new SimpleAuthenticationInfo(principal, password, getName());
        }

        return null;
    }

    public static void main(String[] args) {
        RealmSecurityManager manager = new DefaultSecurityManager();

        EncryptRealm realm = new EncryptRealm();

        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("SHA-256");
        matcher.setHashIterations(1); // 设置 hash 的次数，为了简便，这里只是 hash 一次
        realm.setCredentialsMatcher(matcher);

        manager.setRealm(realm);

        SecurityUtils.setSecurityManager(manager);

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("xhliu", "123456");

        try {
            subject.login(token);
            log.info("登录成功！");
        } catch (UnknownAccountException e) {
            log.trace(e.getLocalizedMessage());
            log.info("输入的账户的账户名不存在");
        } catch (IncorrectCredentialsException e) {
            log.trace(e.getLocalizedMessage());
            log.info("输入的密码错误");
        }
    }
}
