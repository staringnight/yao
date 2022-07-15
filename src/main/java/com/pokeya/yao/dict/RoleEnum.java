package com.pokeya.yao.dict;

import com.pokeya.yao.exception.EnumException;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author chentong
 * @Date 2022/7/12 6:32 PM
 */
@Getter
@AllArgsConstructor
public enum RoleEnum {
    UNKONW(-1, "未知"), TEACHER(0, "教师"), PARENT(1, "家长"),
    ADMIN(2, "超管"), CLASSMANAGER(3, "班管"), RAM(4, "子管");

    private final int id;
    private final String desc;

    public static RoleEnum getById(int id) {
        for (RoleEnum e : RoleEnum.values()) {
            if (e.getId() == id) {
                return e;
            }
        }
        throw new EnumException("错误的Role类型");
    }
}
