package com.nice.dao;

import com.nice.pojo.TowattUser;

public interface TowattUserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(TowattUser record);

    int insertSelective(TowattUser record);

    TowattUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TowattUser record);

    int updateByPrimaryKey(TowattUser record);

    TowattUser findByName(String name);
}