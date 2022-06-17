package com.dazhi100.common.clientcache;

import com.dazhi100.common.exception.ApiException;

public interface EtagStoreManager {

    String get(String key) throws ApiException;

    Boolean del(String key) throws ApiException;

}
