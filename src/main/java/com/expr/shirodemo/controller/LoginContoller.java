package com.expr.shirodemo.controller;

import com.expr.shirodemo.mapper.UserMapper;
import com.expr.shirodemo.model.ResultMap;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginContoller {

    private final ResultMap resultMap;

    private final UserMapper userMapper;
    @Autowired
    public LoginContoller(ResultMap resultMap,UserMapper userMapper){
        this.resultMap = resultMap;
        this.userMapper = userMapper;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResultMap login(String userName, String password){
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);

        subject.login(token);

        String role = userMapper.getRole(userName);

        if ("user".equals(role)){
            return resultMap.success().message("欢迎登陆");
        }else if("admin".equals(role)){
            return resultMap.success().message("欢迎来到管理员界面");
        }
        return resultMap.fail().message("没有登陆权限");
    }

    @RequestMapping(value = "logout",method = RequestMethod.GET)
    public ResultMap logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return resultMap.success().message("注销成功");
    }

    @RequestMapping(value = "/noroles")
    public ResultMap noroles(){
        return resultMap.success().message("没有权限");
    }
}
