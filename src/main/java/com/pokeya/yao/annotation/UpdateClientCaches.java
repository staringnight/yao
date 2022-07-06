package com.pokeya.yao.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UpdateClientCaches {
    UpdateClientCache[] value();
}
