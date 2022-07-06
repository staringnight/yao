package com.pokeya.yao.constant;

import java.time.format.DateTimeFormatter;

public interface TimeConstant {
    //----------------统一的时间处理格式---------------------//
    DateTimeFormatter UniformDateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    DateTimeFormatter UniformDateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;


    //--------------------秒数常量-------------------------//
    int ONE_HOUR = 60 * 60;
    int ONE_DAY = 24 * ONE_HOUR;
    int ONE_WEEK = 7 * ONE_DAY;
    int ONE_MONTH = 30 * ONE_DAY;
}
