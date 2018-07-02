package com.nice.service;

import com.nice.pojo.SysUser;

/**
 * @program: nice-springboot
 * @description:
 * @author: BaoFei
 * @create: 2018-06-29 16:48
 **/

public interface SysUserService {

    int insert(SysUser user);

    SysUser getSysUserById(String id);

    SysUser findByName(String name);

    SysUser getUserByLoginNameAndPassWord(String loginName, String passWord);

    int updateUserByLoginNameAndPassWord(SysUser s);

}
