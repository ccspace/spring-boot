package com.nice.controller;

import com.nice.utils.JSONResult;
import com.nice.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: nice-springboot
 * @description:
 * @author: BaoFei
 * @create: 2018-06-29 11:19
 **/

@RestController
@RequestMapping("redis")
public class RedisController {

    @Autowired
    private StringRedisTemplate strRedis;

    @Autowired
    private RedisOperator redis;

    @RequestMapping("test")
    public JSONResult test(){
        strRedis.opsForValue().set("nice","hello");
        strRedis.opsForValue().set("baixue","爱生活，爱雪姐！");
        return JSONResult.ok();
    }

}
