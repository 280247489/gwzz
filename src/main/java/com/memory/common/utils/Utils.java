package com.memory.common.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.core.io.UrlResource;
import org.springframework.util.DigestUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.List;
import java.util.UUID;
import java.io.IOException;

/**
 * @Auther: cui.Memory
 * @Date: 2018/11/7 0007 16:48
 * @Description:
 */
public class Utils {

    public static final String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };

    /**
     * 生成32位UUID字符串
     */
    public static String generateUUIDs() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    public static void main(String[] args) {
        System.out.println(generateUUIDs());
    }

    public static String getShortUUID() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();
    }

    public static String getShortUUTimeStamp(){return getShortUUID()+ System.currentTimeMillis();}




    /**
     *md5加密
     */
    public static String md5Password(String str){ return  DigestUtils.md5DigestAsHex(str.getBytes()); }


    /**
     * 检测网络资源是否可访问
     * @param url
     * @return
     * @throws MalformedURLException
     */
    public static Boolean isHttpAccess(String url) throws MalformedURLException {
      return   new UrlResource(url).exists();
    }

    /**
     * 向目的URL发送post请求
     * @param url       目的url
     * @param params    发送的参数
     * @return  ResultVO
     */
    public static Object sendPostRequest(String url, MultiValueMap<String, String> params){
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
        // 以表单的方式提交
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //将请求头部和参数合成一个请求
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

       // client.exchange(url, method, requestEntity);
        //执行HTTP请求，将返回的结构使用ResultVO类格式化
        ResponseEntity<Object> response = client.exchange(url, method, requestEntity, Object.class);

        return response.getBody();
    }



    /**
     * 判断是否为基本类型：包括String/Integer/Double/Boolean
     * @param clazz clazz
     * @return  true：是;     false：不是
     */
    public static boolean isPrimite(Class<?> clazz){
        if (clazz.isPrimitive() || clazz == String.class || clazz == Integer.class || clazz == Double.class || clazz == Boolean.class){
            return true;
        }else {
            return false;
        }
    }

    public static Boolean isNotNull(String str){
        return  str!=null && str.length()>0;
    }

    public static Boolean isNotNull(Integer str){
        return  str!=null ;
    }

    public static Boolean isNotNull(Boolean str){
        return  str!=null ;
    }

    public static Boolean isNotNull(MultipartFile file){
        return file!=null && !file.isEmpty();
    }

    public static Boolean isNotNull(Object file){
        return file!=null;
    }

    public static Boolean isNotNull(List<?> list){ return list!=null && list.size()>0; }

    public static Integer getIntVal(Object obj){
        return (Utils.isNotNull(obj)&&!("null").equals(obj))?(Integer.valueOf(obj.toString())):0;
    }




}


