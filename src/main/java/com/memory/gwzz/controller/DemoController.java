package com.memory.gwzz.controller;

import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Auther: cui.Memory
 * @Date: 2018/11/1
 * @Description:
 */
@RestController
public class DemoController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(DemoController.class);

    @RequestMapping("test")
    public Message test() {
        msg = Message.success();
        return msg;
    }
    private static Map<String,String> map = new HashMap<String,String>();
    public static void main(String[] args) {
        map.put("name", "李四");
        map.put("age", "30");
        map.put("sex", "male");
        map.put("code", "3010");
        //方法一：通过key取值
        /*for(String key:map.keySet()){
            System.out.printf("map key is %s and value is %s",key,map.get(key));
            System.out.println();
        }*/
        //方法二：通过迭代器取值
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        Map.Entry<String, String> entry = null;
        while(iterator.hasNext()){
            entry = iterator.next();
//            System.out.printf("key is %s and value is %s",entry.getKey(),entry.getValue());
            if (entry.getValue()=="李四"){
                System.out.printf("key is %s and value is %s",entry.getKey(),entry.getValue());
            }
            System.out.println();
        }
            //通过entryset
        /*for(Entry<String, String> entry:map.entrySet()){
            System.out.printf("key is %s and value is %s",entry.getKey(),entry.getValue());
            System.out.println();
        }*/
        //通过map的value方法实现
        /*for(String value : map.values()){
            System.out.println("value is "+value);
        }*/
    }

}