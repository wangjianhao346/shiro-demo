package com.rain.demo.config;

import com.rain.demo.entity.User;
import com.rain.demo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import javax.annotation.Resource;


public class ShiroRealm extends AuthorizingRealm {
    @Resource
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("执行授权逻辑");
        // 给资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        // 添加资源的授权字符串
        info.addStringPermission("user:add");     // 直接添加授权字符串

        // 从数据库里取授权权限
        Subject subject = SecurityUtils.getSubject();

        User user = (User) subject.getPrincipal();

        info.addStringPermission(user.getRole().toString());

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行认证逻辑");

        UsernamePasswordToken user = (UsernamePasswordToken) token;

        User currentUser = userService.getUser(user.getUsername());

        // 判断用户名
        if (currentUser == null) return null;
        // 判断密码
        else return new SimpleAuthenticationInfo(currentUser, currentUser.getPassword(), "");
    }
}
