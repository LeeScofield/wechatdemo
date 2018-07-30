package com.example.wechatdemo.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1Util {


    public static String getSignForSHA1(String str){

        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return byteToStr(messageDigest.digest(str.getBytes()));
    }

    /**
     * 将字节加工然后转化成字符串
     * @param digest
     * @return
     */
    private static String byteToStr(byte[] digest){

        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<digest.length;i++){
            //将取得字符的二进制码转化为16进制码的的码数字符串
            stringBuilder.append(byteToHexStr(digest[i]));
        }
        return stringBuilder.toString();

    }

    /**
     * 把每个字节加工成一个16位的字符串
     * @param b
     * @return
     */
    private static String byteToHexStr(byte b){
        //转位数参照表
        char[] Digit= {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};


        //位操作把2进制转化为16进制
        char[] tempArr=new char[2];
        tempArr[0]=Digit[(b>>>4)&0X0F];//XXXX&1111那么得到的还是XXXX
        tempArr[1]=Digit[b&0X0F];//XXXX&1111那么得到的还是XXXX

        //得到进制码的字符串
        String s=new String(tempArr);
        return s;
    }
}
