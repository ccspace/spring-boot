package com.nice.controller;

import com.nice.pojo.SysUser;
import com.nice.service.SysUserService;
import com.nice.utils.JSONResult;
import com.nice.utils.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static com.nice.servlet.ValidateCodeServlet.VALIDATE_CODE;

/**
 * @program: nice-springboot
 * @description:
 * @author: BaoFei
 * @create: 2018-06-29 16:44
 **/

@RestController
@RequestMapping("account")
public class AccountController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("register")
    public JSONResult register(SysUser user,String validateCode,HttpSession session){
        String sessionCode = (String) session.getAttribute(VALIDATE_CODE);
        if(StringUtils.isNoneBlank(user.getUsername()) && StringUtils.isNotBlank(user.getPassword())){
            SysUser  userByName = sysUserService.findByName(user.getUsername());
            if(userByName == null){
                if(StringUtils.isNotBlank(validateCode) && sessionCode.toUpperCase().equalsIgnoreCase(validateCode.toUpperCase())){
                    SysUser sysUser = new SysUser();
                    sysUser.setId(UUIDUtils.getUUID());
                    sysUser.setUsername(user.getUsername());
                    sysUser.setPassword(user.getPassword());
                    sysUserService.insert(sysUser);
                }else{
                    return JSONResult.errorMsg("温馨提示 - 图片验证码不正确！");
                }
            }else{
                return JSONResult.errorMsg("温馨提示 - 用户名已存在！");
            }
        }else{
            return JSONResult.errorMsg("温馨提示 - 用户名、密码不能为空！");
        }
        return JSONResult.ok("注册成功！");
    }


    @RequestMapping(value = "id/{id}")
    public JSONResult getSysUserById(@PathVariable String id){
        SysUser user = sysUserService.getSysUserById(id);
        return JSONResult.ok(user);
    }



}
