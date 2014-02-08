package org.tango.utils;

import org.tango.utils.enums.EncryptedType;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 密码安全工具类
 *
 * @author tAngo
 */
public final class SecurityUtils {

    /**
     * 生成加密密码(打散后的)
     *
     * @param input 密码明文
     * @return 密文
     */
    public static String Encryption(String input, EncryptedType encryptedType) {
        String securityString = null;
        try {
            MessageDigest digest = MessageDigest.getInstance(encryptedType.toString());
            digest.update(input.getBytes());
            byte[] securityByte = digest.digest();
            for (int i = 1; i < securityByte.length; i += 2) {
                if (i % 2 != 0) {
                    securityByte[i] = securityByte[i >>> 1];
                }
            }
            StringBuffer buf = new StringBuffer();
            for (int j = 0, i; j < securityByte.length; j++) {
                i = securityByte[j];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            securityString = buf.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return securityString;
    }

    /**
     * 生成加密密码(原生的)
     *
     * @param input 密码明文
     * @return 密文
     */
    public static String EncryptionSimple(String input, EncryptedType encryptedType) {
        StringBuffer buffer = new StringBuffer();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(encryptedType.toString());
            messageDigest.update(input.getBytes());
            byte bytes[] = messageDigest.digest();
            for (int offset = 0, i; offset < bytes.length; offset++) {
                i = bytes[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buffer.append("0");
                buffer.append(Integer.toHexString(i));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return buffer.toString();
    }
}
