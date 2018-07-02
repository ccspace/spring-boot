package com.nice.service.impl;

import com.nice.dao.SysUserMapper;
import com.nice.pojo.SysUser;
import com.nice.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: nice-springboot
 * @description:
 * @author: BaoFei
 * @create: 2018-07-02 15:55
 **/

@Service
public class SysUserServiceImpl implements SysUserService{

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 支持事物回滚
     * @param user
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int insert(SysUser user){
        return sysUserMapper.insert(user);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public SysUser getSysUserById(String id){
        return sysUserMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public SysUser findByName(String name){
        return sysUserMapper.findByName(name);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public SysUser getUserByLoginNameAndPassWord(String loginName, String passWord){
        return sysUserMapper.getUserByLoginNameAndPassWord(loginName,passWord);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int updateUserByLoginNameAndPassWord(SysUser s){
        return sysUserMapper.updateByPrimaryKeySelective(s);
    }

}
