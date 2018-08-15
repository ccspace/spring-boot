package com.nice.service;

import com.nice.pojo.TowattUser;

public interface TowattUserService {

    TowattUser findByName(String name);

    int updateByPrimaryKey(TowattUser record);

    int updateByPrimaryKeySelective(TowattUser record);

}
