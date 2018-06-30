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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.nice.servlet.ValidateCodeServlet.VALIDATE_CODE;
import static com.nice.utils.MD5Util.BIT_32;
import static com.nice.utils.MD5Util.GetMD5Code;

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

    @RequestMapping("login")
    public JSONResult login(HttpServletRequest request, HttpServletResponse response,HttpSession session){
        String loginName = request.getParameter("loginName");
        String passWrod = request.getParameter("passWord");
        String validateCode = request.getParameter("validateCode");
        String sessionCode = (String) session.getAttribute(VALIDATE_CODE);
        if(StringUtils.isNotBlank(loginName) && StringUtils.isNotBlank(passWrod)){
            SysUser user = sysUserService.findByName(loginName);
            if(user != null){
                if(StringUtils.isNotBlank(validateCode)){
                    if(sessionCode.toUpperCase().equalsIgnoreCase(validateCode.toUpperCase())){
                        if(loginName.equals(user.getUsername()) && GetMD5Code(passWrod,BIT_32).equals(user.getPassword()) ){
                            return JSONResult.ok("登陆成功!");
                        }else{
                            return JSONResult.errorMsg("温馨提示 - 登陆失败，请稍后重试!");
                        }
                    }else{
                        return JSONResult.errorMsg("温馨提示 - 验证码不正确!");
                    }
                }else{
                    return JSONResult.errorMsg("温馨提示 - 请输入验证码!");
                }
            }else{
                return JSONResult.errorMsg("温馨提示 - 用户不存在!");
            }
        }else{
            return JSONResult.errorMsg("温馨提示 - 用户名、密码不能为空!");
        }
    }

    @PostMapping("register")
    public JSONResult register(SysUser user,String validateCode,HttpSession session){
        String sessionCode = (String) session.getAttribute(VALIDATE_CODE);
        if(StringUtils.isNoneBlank(user.getUsername()) && StringUtils.isNotBlank(user.getPassword())){
            SysUser userByName = sysUserService.findByName(user.getUsername());
            if(userByName == null){
                if(StringUtils.isNotBlank(validateCode) && sessionCode.toUpperCase().equalsIgnoreCase(validateCode.toUpperCase())){
                    SysUser sysUser = new SysUser();
                    sysUser.setId(UUIDUtils.getUUID());
                    sysUser.setUsername(user.getUsername());
                    sysUser.setPassword(GetMD5Code(user.getPassword(),BIT_32));
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
