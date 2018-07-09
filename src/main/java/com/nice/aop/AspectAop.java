package com.nice.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @program: nice-springboot
 * @description:
 * @author: BaoFei
 * @create: 2018-07-09 10:44
 **/

@Aspect
@Component
public class AspectAop {

    @Pointcut("this(com.nice.service.LoggableService)")
    public void matchCondition(){

    }
    @Before("matchCondition()")
    public void before(){
        System.out.println("");
        System.out.println("### Before");
    }
}
