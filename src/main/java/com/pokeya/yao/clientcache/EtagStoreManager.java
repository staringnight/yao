package com.pokeya.yao.clientcache;

import com.pokeya.yao.exception.ApiException;

public interface EtagStoreManager {

    String get(String key) throws ApiException;

    Boolean del(String key) throws ApiException;

}
