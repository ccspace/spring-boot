package com.nice.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @ProjectName: nice-springboot
 * @Package: com.nice.pojo
 * @ClassName: TowattUser
 * @Description: shiro权限登陆实体
 * @Author: BaoFei.
 * @CreateDate: 2018/8/14 14:42
 * @UpdateUser:
 * @UpdateDate: 2018/8/14 14:42
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * Copyright: Copyright (c) 2018
 */

@Data
public class TowattUser {

    @Id
    @GeneratedValue
    private Integer id;

    private String username;

    private String password;

    private String salt;

}