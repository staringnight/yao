package com.dazhi100.common.dict;


import com.dazhi100.common.dict.base.NromalEnum;
import com.dazhi100.common.exception.EnumException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 开启状态
 */
@Getter//只提供getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)//私有修饰，防止外部调用
public enum ONOFF implements NromalEnum {
    ON(0, "on") {
        @Override
        public boolean isOn() {
            return true;
        }
    },
    OFF(1, "off") {
        @Override
        public boolean isOn() {
            return false;
        }
    };

    /**
     * 枚举天生不可变，所有的域都应声明为final
     */
    private final int id;
    private final String desc;

    /**
     * int转枚举
     *
     * @param id
     * @return
     */
    public static ONOFF of(int id) {
        for (ONOFF e : ONOFF.values()) {
            if (e.getId() == id) {
                return e;
            }
        }
        throw new EnumException("错误的开关状态");
    }

    /**
     * 是否打开
     *
     * @return
     */
    public abstract boolean isOn();
}
