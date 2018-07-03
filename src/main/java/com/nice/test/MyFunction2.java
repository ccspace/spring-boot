package com.nice.test;

/**
 * @program: nice-springboot
 * @description:
 * @author: BaoFei
 * @create: 2018-07-03 14:55
 **/

@FunctionalInterface
public interface MyFunction2<T,R> {

    R getValue(T t1, T t2);


}
