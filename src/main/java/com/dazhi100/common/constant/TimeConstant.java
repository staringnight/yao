package com.dazhi100.common.constant;

import java.time.format.DateTimeFormatter;

public class TimeConstant {
    //----------------统一的时间处理格式---------------------//
    public static final DateTimeFormatter UniformDateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    public static final DateTimeFormatter UniformDateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;


    //--------------------秒数常量-------------------------//
    public static final int ONE_HOUR = 60 * 60;
    public static final int ONE_DAY = 24 * ONE_HOUR;
    public static final int ONE_WEEK = 7 * ONE_DAY;
    public static final int ONE_MONTH = 30 * ONE_DAY;
}
