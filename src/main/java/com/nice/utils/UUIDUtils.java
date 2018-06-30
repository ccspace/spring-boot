package com.nice.utils;

import java.util.UUID;

public class UUIDUtils {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","").toLowerCase();
    }

    public static void main(String[] args) {
        System.out.println(getUUID());
    }
}
