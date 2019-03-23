package com.expr.shirodemo.config;
import com.expr.shirodemo.shrio.CustomRealm;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        shiroFilterFactoryBean.setSecurityManager(securityManager);

        shiroFilterFactoryBean.setLoginUrl("/notLogin");

        shiroFilterFactoryBean.setUnauthorizedUrl("/notRole");

        //设置拦截器
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        filterChainDefinitionMap.put("/guest/**","anon");

        filterChainDefinitionMap.put("/user/**","roles[user]");

        filterChainDefinitionMap.put("/admin/**","roles[admin]");

        filterChainDefinitionMap.put("/login","anon");
        //其余接口一律拦截
        //这行代码要放在最后，要不然会导致所有的url都被拦截
        filterChainDefinitionMap.put("/**","authc");
        System.out.println("shiro拦截器工厂注入成功");
        return shiroFilterFactoryBean;
    }
    @Bean
    public SecurityManager securityManager(CustomRealm customRealm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(customRealm);
        return defaultWebSecurityManager;
    }
}
