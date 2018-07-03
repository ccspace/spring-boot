package com.nice.test;

/**
 * @program: nice-springboot
 * @description:
 * @author: BaoFei
 * @create: 2018-07-03 10:44
 **/
public class Lambda {


    public static void main(String[] args) {

        String s = strHander("  nice", str -> str.toUpperCase());
        System.out.println(s);
    }

    public static String strHander(String str, MyFunction function){
        return function.getValue(str);
    }

}
