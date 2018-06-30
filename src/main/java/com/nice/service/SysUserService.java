package com.nice.service;

import com.nice.dao.SysUserMapper;
import com.nice.pojo.SysUser;
import com.nice.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: nice-springboot
 * @description:
 * @author: BaoFei
 * @create: 2018-06-29 16:48
 **/

@Service
public class SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    public int insert(SysUser user){
        return sysUserMapper.insert(user);
    }

    public SysUser getSysUserById(String id){
        return sysUserMapper.selectByPrimaryKey(id);
    }

    public SysUser findByName(String name){
        return sysUserMapper.findByName(name);
    }

}
