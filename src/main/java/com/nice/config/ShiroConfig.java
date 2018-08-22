package com.nice.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;


/**
 * @ProjectName: nice-springboot
 * @Package: com.nice.config
 * @ClassName: ShiroConfig
 * @Description: shiro配置
 * @Author: BaoFei.
 * @CreateDate: 2018/8/14 14:52
 * @UpdateUser:
 * @UpdateDate: 2018/8/14 14:52
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * Copyright: Copyright (c) 2018
 */

@Configuration
public class ShiroConfig {

    @Bean(name="shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/th/login");
        shiroFilterFactoryBean.setSuccessUrl("/index");

//        //未授权页面
//        shiroFilterFactoryBean.setUnauthorizedUrl("/err");
//
//        //配置访问权限
//        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
//        filterChainDefinitionMap.put("/login", "anon");
//        filterChainDefinitionMap.put("/register","anon");
//        filterChainDefinitionMap.put("/swagger-ui.html","anon");
//
//        //配置退出 shiro已经实现退出
//        filterChainDefinitionMap.put("/logout","logout");
//
//        //其他请求需要认证
//        filterChainDefinitionMap.put("/**", "authc");
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;

    }

    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
        defaultSecurityManager.setRealm(securityRealm());
        return defaultSecurityManager;
    }

    @Bean
    public SecurityRealm securityRealm(){
        SecurityRealm securityRealm = new SecurityRealm();
        securityRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return securityRealm;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher  hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");          //md5算法
        hashedCredentialsMatcher.setHashIterations(1024);              //散列次数
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true); //散列后为16进制
        return hashedCredentialsMatcher;
    }


}
