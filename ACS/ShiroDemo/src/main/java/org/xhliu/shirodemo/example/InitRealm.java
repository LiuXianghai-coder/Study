package org.xhliu.shirodemo.example;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitRealm {
    private final static Logger log = LoggerFactory.getLogger(InitRealm.class);

    public static void main(String[] args) {
        // 创建 RealmSecurityManager
        RealmSecurityManager manager = new DefaultSecurityManager();
        manager.setRealm(new IniRealm("classpath:shiro.ini"));

        // 设置安装工具中的默认安全管理器
        SecurityUtils.setSecurityManager(manager);

        // 获取主题对象，一个 Subject 代表了一个单独应用用户的状态和安全相关的操作
        Subject subject = SecurityUtils.getSubject();

        // 假设现在有个有用户输入了账户名和密码，现在通过该账户名和密码生成对应的 token
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "123");
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
