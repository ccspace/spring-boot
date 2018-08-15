package com.nice.controller;

import com.nice.pojo.TowattUser;
import com.nice.service.TowattUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @ProjectName: nice-springboot
 * @Package: com.nice.controller
 * @ClassName: TowattController
 * @Description: 登陆验证
 * @Author: BaoFei.
 * @CreateDate: 2018/8/14 14:25
 * @UpdateUser:
 * @UpdateDate: 2018/8/14 14:25
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * Copyright: Copyright (c) 2018
 */

@Controller
@RequestMapping("towatt")
public class TowattController {

    @Autowired
    private TowattUserService towattUserService;

    @RequestMapping("doRegister")
    public String doRegister(@RequestParam("username")String userName,
                             @RequestParam("password")String passWd){

        try{
            TowattUser u = towattUserService.findByName(userName);
            if(u == null){
                ByteSource salt = ByteSource.Util.bytes(userName);
                String newPwd = new SimpleHash("md5",passWd,salt,1024).toHex();
                TowattUser user = new TowattUser();
                user.setUsername(userName);
                user.setPassword(newPwd);
                user.setSalt(salt.toString());
                towattUserService.insert(user);
            }else{
                System.out.println("用户名已经被占用！");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "thymeleaf/success";
    }

    @RequestMapping("doLogin")
    public String doLogin(TowattUser user){
        String userName = user.getUsername();
        System.out.println("当前用户：" + user.getUsername());
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());

        Subject subject = SecurityUtils.getSubject();
        token.setRememberMe(true);
        try{
             subject.login(token);
        }catch (IncorrectCredentialsException ice){
            System.out.println( "对用户【" + userName +"】进行登录验证，验证未通过，错误的凭证！");
            ice.printStackTrace();
        }catch (UnknownAccountException uae){
            System.out.println("对用户【" + userName +"】进行登录验证，验证未通过，未知账户！");
        }catch (LockedAccountException lae){
            System.out.println("对用户【" + userName +"】进行登录验证，验证未通过，账户锁定！");
            lae.printStackTrace();
        }catch (ExcessiveAttemptsException eae){
            System.out.println("对用户【" + userName +"】进行登录验证，验证未通过，错误次数太多！");
            eae.printStackTrace();
        }catch (AuthenticationException ae){
            System.out.println("对用户【" + userName +"】进行登录验证，验证未通过，用户名、密码不正确！");
            ae.printStackTrace();
        }

        if(subject.isAuthenticated()){
            System.out.println("登陆成功！！！");
            return "thymeleaf/index";
        }else{
            token.clear();
            return "thymeleaf/dologin";
        }
    }



}
