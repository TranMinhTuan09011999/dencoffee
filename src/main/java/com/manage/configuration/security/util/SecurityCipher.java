package com.manage.configuration.security.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

public class SecurityCipher {

    private static final String KEYVALUE = "secureCDCKey";
    private static SecretKeySpec secretKey;

    // key was encrypt from KEYVALUE ---> Because when KEYVALUE encrypt, sometime get different value
    private static byte[] key = {-78, 36, -33, 50, 25, 56, 66, 39, -103, -92, 11, -51, -102, -47, 103, -24};

    private SecurityCipher() {
        throw new AssertionError("Static!");
    }

    public static void setKey() {
        MessageDigest sha;
        try {
//            key = KEYVALUE.getBytes(StandardCharsets.UTF_8);
//            sha = MessageDigest.getInstance("SHA-1");
//            key = sha.digest(key);
//            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(String strToEncrypt) {
        if (strToEncrypt == null) return null;
        try {
            setKey();
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String strToDecrypt) {
        if (strToDecrypt == null) return null;
        try {
            setKey();
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
