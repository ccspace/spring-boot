package com.nice.config;

import com.nice.pojo.TowattUser;
import com.nice.service.TowattUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: nice-springboot
 * @Package: com.nice.config
 * @ClassName: SecurityRealm
 * @Description: SecurityRealm重写授权和认证
 * @Author: BaoFei.
 * @CreateDate: 2018/8/14 16:49
 * @UpdateUser:
 * @UpdateDate: 2018/8/14 16:49
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * Copyright: Copyright (c) 2018
 */
public class SecurityRealm extends AuthorizingRealm{

    @Autowired
    TowattUserService towattUserService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String userName = (String)principalCollection.getPrimaryPrincipal();
        List<String> roles = new ArrayList<String>();
        List<String> prems = new ArrayList<String>();
        roles.add(userName);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRoles(roles);
        authorizationInfo.addStringPermissions(prems);
        return authorizationInfo;
    }

    //验证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        String userName = (String)authenticationToken.getPrincipal();

        TowattUser u = towattUserService.findByName(userName);
        if(u == null){
            throw new UnknownAccountException("没找到帐号！");    //没找到帐号
        }
        Object principal = u.getUsername();
        Object credential = u.getPassword();
        String realName = getName();
        ByteSource salt = ByteSource.Util.bytes(u.getUsername()); //对用户名进行加盐
//        u.setSalt(salt.toString());
//        towattUserService.updateByPrimaryKeySelective(u);

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                principal,    //用户名
                credential,   //密码
                salt,         //对密码进行加盐
                realName
        );
        return authenticationInfo;
    }
}
