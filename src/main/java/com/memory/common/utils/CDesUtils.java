package com.memory.common.utils;

import com.sun.crypto.provider.SunJCE;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;

/**
 * @Auther: cui.Memory
 * @Date: 2018/11/7 0007 14:00
 * @Description: DES加密解密工具类
 */
public class CDesUtils {
    private static String strDefaultKey = "cui.Memory";
    private Cipher encryptCipher;
    private Cipher decryptCipher;
    private static String encryptAlgorithm = "DES";

    public static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        StringBuffer sb = new StringBuffer(iLen * 2);

        for(int i = 0; i < iLen; ++i) {
            int intTmp;
            for(intTmp = arrB[i]; intTmp < 0; intTmp += 256) {
                ;
            }

            if(intTmp < 16) {
                sb.append("0");
            }

            sb.append(Integer.toString(intTmp, 16));
        }

        return sb.toString();
    }

    public static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        byte[] arrOut = new byte[iLen / 2];

        for(int i = 0; i < iLen; i += 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte)Integer.parseInt(strTmp, 16);
        }

        return arrOut;
    }

    public CDesUtils() throws Exception {
        this(strDefaultKey);
    }

    public CDesUtils(String strKey) throws Exception {
        this.encryptCipher = null;
        this.decryptCipher = null;
        Security.addProvider(new SunJCE());
        Key key = this.getKey(strKey.getBytes());
        this.encryptCipher = Cipher.getInstance(encryptAlgorithm);
        this.encryptCipher.init(1, key);
        this.decryptCipher = Cipher.getInstance(encryptAlgorithm);
        this.decryptCipher.init(2, key);
    }

    public byte[] encrypt(byte[] arrB) throws Exception {
        return this.encryptCipher.doFinal(arrB);
    }

    public String encrypt(String strIn) throws Exception {
        return byteArr2HexStr(this.encrypt(strIn.getBytes()));
    }

    public byte[] decrypt(byte[] arrB) throws Exception {
        return this.decryptCipher.doFinal(arrB);
    }

    public String decrypt(String strIn) throws Exception {
        return new String(this.decrypt(hexStr2ByteArr(strIn)));
    }

    private Key getKey(byte[] arrBTmp) throws Exception {
        byte[] arrB = new byte[8];

        for(int _generator = 0; _generator < arrBTmp.length && _generator < arrB.length; ++_generator) {
            arrB[_generator] = arrBTmp[_generator];
        }

        KeyGenerator var6 = KeyGenerator.getInstance(encryptAlgorithm);
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(arrB);
        var6.init(secureRandom);
        SecretKey key = var6.generateKey();
        return key;
    }
}
