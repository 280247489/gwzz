package com.memory.redis;

import com.memory.common.yml.MyFileConfig;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: cui.Memory
 * @Date: 2019/3/28 0028 15:28
 * @Description:
 */
public class CacheConstantConfig {


    @Autowired
    private static MyFileConfig config;

    //APP数据Hash
    public static final String SYSAPPDATA = "sysAppData";

    //课程拓展数据
    public static final String COURSEEXT = "courseExt";

    public static final String COURSE ="course";

    //课程直播课程拓展数据
    public static final String COURSEEXTHASH ="courseExt:hash:";


    /**
     * 课程直播 start========
     */
    //课程直播分享用户数据
    public static final String SHARECOURSEVIEW = "share:course:view:";

    //课程直播分享次数统计
    public static final String SHARECOURSEVIEWOPENID ="share:course:view_openid:";

   //课程直播内容信息
    public static final String SHARECOURSECONTENT ="share:course:content:";

    //课程直播内存信息
    public static Map<String,Object> COURSEMAP = new HashMap<String,Object>();
    /**
     * 课程直播 end========
     */



    /**
     * 微信分享 start========
     */
    //微信公众平台基础服务access_token（每天有数量限制）
    public static String WECHATSHAREACCESSTOKEN = "wechat:platform:share:access_token";
    //微信公众平台基础服务jsapi_ticket（每天有数量限制）
    public static String WECHATSHAREJSAPITICKET ="wechat:platform:share:jsapi_ticket";
    /**
     * 微信分享 end========
     */





}
