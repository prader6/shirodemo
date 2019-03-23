package com.expr.shirodemo.shrio;

import com.expr.shirodemo.mapper.UserMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sound.sampled.Line;
import java.util.HashSet;

@Component
public class CustomRealm extends AuthorizingRealm {

    private final UserMapper userMapper;
    @Autowired
    public CustomRealm(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("进入授权方法");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();

        String role = userMapper.getRole(username);

        HashSet<String> set = new HashSet<>();
        //将role封装到set作为set的参数
        set.add(role);
        authorizationInfo.setRoles(set);

        return authorizationInfo;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("进入身份认证方法");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //进行密码验证
        String password = userMapper.getPassword(token.getUsername());
        if (password == null){
            throw new AccountException("用户名不正确");
        }else if (!password.equals((new String((char[])token.getCredentials())))){
            throw new AccountException("密码不正确");
        }
        return new SimpleAuthenticationInfo(token.getPrincipal(),password,getName());
    }
}
