package com.rain.demo.controller;

import com.rain.demo.entity.Result;
import com.rain.demo.entity.User;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/")
public class Start {

    @RequestMapping("login")
    @ResponseBody
    public Result login(User user){
        Result result = new Result();
        result.setMessage("用户名不能为空");
        if (null == user.getUsername())
            return result;

        /* 编写用户认证操作 */

        // 1、获取Subject
        Subject subject = SecurityUtils.getSubject();

        // 2、封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());

        try {
            // 3、执行登录方法
            subject.login(token);
        } catch (UnknownAccountException uae) {
            result.setMessage("未知账户");
        } catch (IncorrectCredentialsException ice) {
            result.setMessage("密码不正确");
        } catch (LockedAccountException lae) {
            result.setMessage("账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            result.setMessage("用户名或密码错误次数过多");
        } catch (AuthenticationException ae) {
            result.setMessage("用户名或密码不正确");
        }
        // 验证是否登录成功
        if (subject.isAuthenticated()) {
            result.setMessage("登录成功");
            System.out.println("登录成功");
        } else {
            token.clear();
            System.out.println("重新登录");
        }
        return result;
    }

    @RequestMapping("add")
    public String add(){
        return "add";
    }

    @RequestMapping("403")
    public String noAuth(){
        return "403";
    }

    @RequestMapping("update")
    public String update(){
        return "update";
    }

    @RequestMapping("index")
    public String index(){
        return "index";
    }

    @RequestMapping("noLogin")
    @ResponseBody
    public Result noLogin(){
        Result result = new Result();
        result.setMessage("被拦截了");
        return result;
    }

    @RequestMapping("logout")
    public void logout() {
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
        } catch (Exception e) {
            System.out.println("error");
        }
    }
}
