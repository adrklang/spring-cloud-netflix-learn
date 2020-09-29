package com.lhstack.microservice.common.util;

/**
 * @author lh
 * @date 2020/9/29
 * @description
 * @name microservice
 * @sine 11
 */
public class DateUtils {

    public static long getTimestamp(){
        return System.currentTimeMillis() / 1000;
    }
}
