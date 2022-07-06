package com.pokeya.yao;

import com.pokeya.yao.bean.Result;
import com.pokeya.yao.constant.TimeConstant;
import com.pokeya.yao.utils.JSON;

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
