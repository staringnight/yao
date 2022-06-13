package com.dazhi100.common.clientcache;

public interface EtagStoreManager {

    String get(String key);

    String update(String key);

    boolean del(String key);

}
