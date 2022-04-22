package com.dazhi100.common.utils;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;

/**
 * uuid
 */
public class UUIDUtil {
    public static String getUUID() {
        return IdUtil.fastSimpleUUID();
    }

    public static Long getUUIDLong() {
        return UUID.fastUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }

}
