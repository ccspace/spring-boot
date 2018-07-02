package com.nice.controller;

import com.nice.pojo.SysUser;
import com.nice.service.SysUserService;
import com.nice.utils.JSONResult;
import com.nice.utils.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Date;

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

    /**
     * 登录
     * @param request
     * @param response
     * @param session
     * @return
     */
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
                        if(loginName.equals(user.getLoginName()) && GetMD5Code(passWrod,BIT_32).equals(user.getPassWord()) ){
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
                return JSONResult.errorMsg("温馨提示 - 用户名、密码错误!");
            }
        }else{
            return JSONResult.errorMsg("温馨提示 - 用户名、密码不能为空!");
        }
    }

    /**
     * 注册
     * @param user
     * @param validateCode
     * @param session
     * @return
     */
    @PostMapping("register")
    public JSONResult register(SysUser user,String validateCode,HttpSession session){
        String sessionCode = (String) session.getAttribute(VALIDATE_CODE);
        if(StringUtils.isNotBlank(user.getLoginName()) && StringUtils.isNotBlank(user.getPassWord())){
            SysUser userByName = sysUserService.findByName(user.getLoginName());
            if(userByName == null){
                if(StringUtils.isNotBlank(validateCode) && sessionCode.toUpperCase().equalsIgnoreCase(validateCode.toUpperCase())){
                    SysUser sysUser = new SysUser();
                    sysUser.setId(UUIDUtils.getUUID());
                    sysUser.setLoginName(user.getLoginName());
                    sysUser.setPassWord(GetMD5Code(user.getPassWord(),BIT_32));
                    sysUser.setRegistTime(new Date());
                    int i = sysUserService.insert(sysUser);
                    if(i > 0){
                        return JSONResult.ok("恭喜您，注册成功！");
                    }else{
                        return JSONResult.errorMsg("温馨提示 - 服务器繁忙，请稍后重试！");
                    }
                }else{
                    return JSONResult.errorMsg("温馨提示 - 图片验证码不正确！");
                }
            }else{
                return JSONResult.errorMsg("温馨提示 - 用户名已存在！");
            }
        }else{
            return JSONResult.errorMsg("温馨提示 - 用户名、密码不能为空！");
        }
    }

    @RequestMapping("passWordManage")
    public JSONResult passWordManage(HttpServletRequest request){
        String loginName = request.getParameter("loginName");
        String passWrodOld = request.getParameter("passWrod");
        String passWrodNew = request.getParameter("passWrodNew");
        if(StringUtils.isNotBlank(loginName) && StringUtils.isNotBlank(passWrodOld)){
            SysUser user = sysUserService.getUserByLoginNameAndPassWord(loginName,passWrodOld);
            if(user != null){
                user.setPassWord(passWrodNew);
                int success = sysUserService.updateUserByLoginNameAndPassWord(user);
                if(success > 0 ){
                    return JSONResult.ok("更新密码成功!");
                }else{
                    return JSONResult.errorMsg("温馨提示 - 更新密码失败,请稍后重试!");
                }
            }else{
                return JSONResult.errorMap("温馨提示 - 请检查用户名、密码是否正确!");
            }
        }else{
            return JSONResult.errorMap("温馨提示 - 请检查用户名、密码是否正确!");
        }
    }

    @RequestMapping(value = "id/{id}")
    public JSONResult getSysUserById(@PathVariable String id){
        SysUser user = sysUserService.getSysUserById(id);
        return JSONResult.ok(user);
    }

}
