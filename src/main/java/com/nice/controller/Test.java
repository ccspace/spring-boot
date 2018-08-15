package com.nice.controller;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @ProjectName: nice-springboot
 * @Package: com.nice.controller
 * @ClassName: Test
 * @Description: java类作用描述
 * @Author: BaoFei.
 * @CreateDate: 2018/8/14 20:24
 * @UpdateUser:
 * @UpdateDate: 2018/8/14 20:24
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * Copyright: Copyright (c) 2018
 */
public class Test {


    public static void main(String[] args) {

     //   System.out.println(md5("123456","nice"));

    }

    public static final String md5(String password, String salt){
        //加密方式
        String hashAlgorithmName = "MD5";
        //盐：为了即使相同的密码不同的盐加密后的结果也不同
        ByteSource byteSalt = ByteSource.Util.bytes(salt);
        //密码
        Object source = password;
        //加密次数
        int hashIterations = 1024;
        SimpleHash result = new SimpleHash(hashAlgorithmName, source, byteSalt, hashIterations);
        return result.toString();
    }

}
