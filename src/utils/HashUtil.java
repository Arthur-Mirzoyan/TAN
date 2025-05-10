// https://chatgpt.com/share/681a1995-86f8-8005-9f13-2d9a5a3072db

package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class for performing hashing operations.
 */
public class HashUtil {

    /**
     * Converts the given input string to its MD5 hash in hexadecimal format.
     *
     * @param input the input string to hash
     * @return the MD5 hash as a hexadecimal string
     * @throws RuntimeException if MD5 algorithm is not available
     */
    public static String toMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            StringBuilder hexString = new StringBuilder();
            byte[] messageDigest = md.digest(input.getBytes());

            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }
}

