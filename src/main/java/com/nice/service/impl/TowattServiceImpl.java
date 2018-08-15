package com.nice.service.impl;

import com.nice.dao.TowattUserMapper;
import com.nice.pojo.TowattUser;
import com.nice.service.TowattUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: nice-springboot
 * @Package: com.nice.service.impl
 * @ClassName: TowattServiceImpl
 * @Description: java类作用描述
 * @Author: BaoFei.
 * @CreateDate: 2018/8/14 18:03
 * @UpdateUser:
 * @UpdateDate: 2018/8/14 18:03
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * Copyright: Copyright (c) 2018
 */

@Service
public class TowattServiceImpl  implements TowattUserService {

    @Autowired
    private TowattUserMapper towattUserMapper;

    @Override
    public int insert(TowattUser record) {
        return towattUserMapper.insert(record);
    }

    @Override
    public TowattUser findByName(String name) {
        return towattUserMapper.findByName(name);
    }

    @Override
    public int updateByPrimaryKey(TowattUser record) {
        return towattUserMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(TowattUser record) {
        return towattUserMapper.updateByPrimaryKeySelective(record);
    }
}
