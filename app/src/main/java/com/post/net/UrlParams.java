package com.post.net;

import java.security.MessageDigest;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhangyao on 16/8/8.
 */
public class UrlParams {

    public static String api_key = "0b19c2b93687347e95c6b6f5cc91bb87";

    public static String genSign(Map<String, String> paramsMap) {

        StringBuffer sb = new StringBuffer();
        paramsMap.put("api_key", api_key);
        // sb.append("api_key=").append(APIConstant.API.api_key);
        Set<String> keySet = paramsMap.keySet();
        for (String key : keySet) {
            sb.append(key).append("=").append(paramsMap.get(key));
        }
        sb.append("47b41864d64bd46");
        String strParam = sb.toString();
        String sign = md5(strParam);

        return sign;
    }

    public static String md5(String strToBeMd5) {
        MessageDigest digester;
        try {
            digester = MessageDigest.getInstance("MD5");

            byte[] array = digester.digest(strToBeMd5.getBytes("UTF-8"));

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
