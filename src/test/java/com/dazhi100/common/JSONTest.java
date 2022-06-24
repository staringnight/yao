package com.dazhi100.common;

import com.dazhi100.common.bean.Result;
import com.dazhi100.common.constant.TimeConstant;
import com.dazhi100.common.utils.JSON;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class JSONTest {
    public static void main(String[] args) {
        String time = JSON.toJSONString(LocalDateTime.now());
        System.out.println(time);
        System.out.println(LocalDate.now().format(TimeConstant.UniformDateFormatter));

        LocalDate date = JSON.parseObject("\"2022-06-24T08:47:48\"", LocalDate.class);
        LocalDateTime dateTime = JSON.parseObject("\"2022-06-24T08:47:48\"", LocalDateTime.class);
        Result<LocalDate> success = Result.success(LocalDate.now());
        System.out.println(JSON.toJSONString(success));
    }
}
