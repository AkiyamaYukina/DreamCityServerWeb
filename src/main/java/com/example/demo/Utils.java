package com.example.demo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Utils {

    public static String GeneratorAuthmeEncryptPassword(String password)
    {
        String salt = Utils.GeneratorRandomSalt(16);
        String password_sha256 = Utils.getSHA256StrJava(Utils.getSHA256StrJava(password)+salt);

        String result = "$SHA$" + salt + "$" + password_sha256;

        return result;
    }

    public static String GeneratorRandomMailCode(int len)
    {
        StringBuilder result = new StringBuilder();
        char[] table = new char[] {'0','1','2','3','4','5','6','7','8','9'};

        for(int i = 0 ; i < len ; i ++)
        {
            result.append(table[new Random().nextInt(table.length)]);
        }
        return result.toString();
    }

    public static String GeneratorRandomSalt(int len)
    {
        StringBuilder result = new StringBuilder();
        char[] table = new char[] {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

        for(int i = 0 ; i < len ; i ++)
        {
            result.append(table[new Random().nextInt(table.length)]);
        }
        return result.toString();
    }

    public static String getSHA256StrJava(String str){
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    private static String byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
}
