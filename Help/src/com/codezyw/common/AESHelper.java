
package com.codezyw.common;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;

public class AESHelper {
    /** 加密key与向量长度为16位 **/
    public static final int KEY_LENGTH = 16;

    public static String Encrypt(String src, String key, String siv) {
        if (key == null || key.length() != 16 || siv == null) {
            return null;
        }
        try {
            byte[] dataBytes = src.getBytes("utf-8");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes("utf-8"), "AES");
            // "算法/模式/补码方式"
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int plaintextLength = dataBytes.length;
            int blockSize = cipher.getBlockSize();
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            IvParameterSpec iv = new IvParameterSpec(siv.getBytes("utf-8"));// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, iv);
            byte[] encrypted = cipher.doFinal(plaintext);
            return Base64.encodeToString(encrypted, Base64.DEFAULT);// 使用BASE64做转码功能，同时能起到2次加密的作用。
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String Decrypt(String sSrc, String sKey, String siv) {
        try {
            if (sKey == null || sKey.length() != 16 || siv == null) {
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            IvParameterSpec iv = new IvParameterSpec(siv.getBytes("utf-8"));
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            // 先用base64解密
            byte[] encrypted1 = Base64.decode(sSrc, Base64.DEFAULT);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            originalString = originalString.trim();
            return originalString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对字符串不满足指定长度的在右侧补空格
     */
    public static String PadRight(String targetStr) {
        int curLength = targetStr.getBytes().length;
        if (targetStr != null && curLength > KEY_LENGTH)
            targetStr = SubStringByte(targetStr);
        String newString = "";
        int cutLength = KEY_LENGTH - targetStr.getBytes().length;
        for (int i = 0; i < cutLength; i++)
            newString += " ";
        String preString = targetStr + newString;
        return preString;
    }

    public static String SubStringByte(String targetStr) {
        while (targetStr.getBytes().length > KEY_LENGTH)
            targetStr = targetStr.substring(0, targetStr.length() - 1);
        return targetStr;
    }

    /**
     * 从普通字符串转换为适用于URL的Base64编码字符串
     */
    public static String Base64Replace(String normalString) {
        return normalString.replace('+', '*').replace('/', '-')
                .replace('=', '.');
    }

    /**
     * 从普通字符串转换为适用于URL的Base64编码字符串
     */
    public static String Base64Restore(String base64String) {
        return base64String.replace('.', '=').replace('*', '+')
                .replace('-', '/');
    }

    /**
     * 为防止base64的字符串与url特殊字符冲突，转换之后几个特殊字符要转义下
     */
    public static String aesEncryptUrl(String key, String data) {
        byte[] r = aesEncrypt(key, data);
        if (r == null) {
            return null;
        }
        String result = new String(r);
        result = result.replace('+', '*').replace('/', '-').replace('=', '.');
        return result;
    }

    public static byte[] aesEncrypt(String key, String data) {
        byte[] encrypted = null;
        try {
            IvParameterSpec zeroIv = new IvParameterSpec(key.getBytes());// 偏移量
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");// 生成密钥
            // PKCS5Padding
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, zeroIv);
            encrypted = cipher.doFinal(plaintext);
            encrypted = Base64.encode(encrypted, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encrypted;
    }
}
