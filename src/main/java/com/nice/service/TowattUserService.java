package com.nice.service;

import com.nice.pojo.TowattUser;

public interface TowattUserService {

    int insert(TowattUser record);

    TowattUser findByName(String name);

    int updateByPrimaryKey(TowattUser record);

    int updateByPrimaryKeySelective(TowattUser record);

}
