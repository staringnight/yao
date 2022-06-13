package com.dazhi100.common.clientcache;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CacheField {
    SID(1, "sid"), CID(2, "cid"), STUID(3, "stuId"), OTHER(4, "other");

    private final int index;
    private final String field;

}
