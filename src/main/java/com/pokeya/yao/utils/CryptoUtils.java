package com.pokeya.yao.utils;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import com.pokeya.yao.constant.ApplicationConstant;

/**
 * crypto
 **/
public class CryptoUtils {

  public static String getSha1(String text) {
    return SecureUtil.sha1(text);
  }

  public static String getSha256(String text) {
    return SecureUtil.sha256(text);
  }

  public static String md5(String text) {
    return SecureUtil.md5(text);
  }

  public static String aesEncode(String text) {
    byte[] key = ApplicationConstant.testAesKey;
    AES aes = SecureUtil.aes(key);
    return aes.encryptHex(text);
  }

  public static String aesDecode(String text) {
    byte[] key = ApplicationConstant.testAesKey;
    AES aes = SecureUtil.aes(key);
    return aes.decryptStr(text);
  }

}
