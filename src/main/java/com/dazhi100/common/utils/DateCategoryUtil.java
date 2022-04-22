package com.dazhi100.common.utils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 工作日判断，不包括自定义假期以及寒暑假,如有必要，请配合学校服务一起使用
 */
public class DateCategoryUtil {
    //-----------------------------------data---------------------------//
    private static final ImmutableList<String> holiday2021 = ImmutableList.of("2021-09-10", "2021-09-11", "2021-09-12",
            "2021-10-01", "2021-10-02", "2021-10-03", "2021-10-04", "2021-10-05", "2021-10-06", "2021-10-07");
    private static final ImmutableList<String> holiday2022 = ImmutableList.of("2022-01-01", "2022-01-02", "2022-01-03", "2022-01-31", "2022-02-01",
            "2022-02-02", "2022-02-03", "2022-02-04", "2022-02-05", "2022-02-06", "2022-02-01", "2022-04-03", "2022-04-04",
            "2022-04-05", "2022-04-30", "2022-05-01", "2022-05-02", "2022-05-03", "2022-05-04", "2022-06-03",
            "2022-09-12", "2022-10-03", "2022-10-04", "2022-10-05", "2022-10-06", "2022-10-07");
    //调休工作日
    private static final ImmutableList<String> exchange2021 = ImmutableList.of("2021-04-24", "2021-05-07", "2021-10-09");
    private static final ImmutableList<String> exchange2022 = ImmutableList.of("2022-01-29", "2022-01-30", "2022-04-02", "2022-04-24", "2022-05-07", "2022-10-08", "2022-10-09");
    private static final ImmutableListMultimap<Integer, String> holidayMap = new ImmutableListMultimap.Builder<Integer, String>()
            .putAll(2021, holiday2021)
            .putAll(2022, holiday2022)
            .build();
    private static final ImmutableListMultimap<Integer, String> exchangeMap = new ImmutableListMultimap.Builder<Integer, String>()
            .putAll(2021, exchange2021)
            .putAll(2022, exchange2022)
            .build();

    //---------------------------------logic-------------------------//

    public static boolean isWorkDay(LocalDate date) {
        return isWeekend(date) ? isExchange(date) : !isHoliday(date);
    }

    /**
     * 判断是否是周末，是周末返回true
     *
     * @param date
     * @return
     */
    public static boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    /**
     * 判断是否是假期，是假期返回true
     *
     * @param date
     * @return
     */
    public static boolean isHoliday(LocalDate date) {
        String dateStr = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        return holidayMap.get(date.getYear()).stream()
                .anyMatch(t -> t.equals(dateStr));
    }

    /**
     * 判断是否是调休工作日，是调休工作日返回true
     *
     * @param date
     * @return
     */
    public static boolean isExchange(LocalDate date) {
        String dateStr = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        return exchangeMap.get(date.getYear()).stream()
                .anyMatch(t -> t.equals(dateStr));
    }

}
