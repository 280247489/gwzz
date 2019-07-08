package com.memory.redis;

import com.memory.common.yml.MyFileConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: cui.Memory
 * @Date: 2019/3/28 0028 15:28
 * @Description:
 */

@Component
public class CacheConstantConfig {

    //APP数据Hash
    public static final String SYSAPPDATA = "sysAppData";

    //课程拓展数据
    public static final String COURSEEXT = "courseExt";

    public static final String COURSE ="course";

    //课程直播课程拓展数据
    public static final String COURSEEXTHASH ="courseExt:hash:";


    /**
     * 课程直播 redis 键值存储规范 start========
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
     * 课程直播 键值存储规范 end========
     */


    /**
     * live 直播kv start============
     */

    //阅读量
    //key ：  Course:view:uuid
    //value ：count
    public static final String COURSEVIEW = "Course:view:";

    //阅读用户统计
    //  key : Course:view_id:uuid;
    //  value :map==> openid:count
    public static final String COURSEVIEWID="Course:view_id:";

    //ios app 阅读量
    //key ： Course:view:ios:app:uuid
    //value ：count
    public static final String COURSEVIEWIOSAPP="Course:view:ios:app:";

    //ios h5 阅读量
    //key ： Course:view:ios:h5:uuid
    //value ：count
    public static final String COURSEVIEWIOSH5="Course:view:ios:h5:";

    //android app 阅读量
    //key ： Course:view:android:app:uuid
    //value ：count
    public static final String COURSEVIEWANDROIDAPP="Course:view:android:app:";

    //android h5 阅读量
    //key ： Course:view:android:h5:uuid
    //value ：count
    public static final String COURSEVIEWANDROIDH5="Course:view:android:h5:";

    //直播内容
    public static final String COURSECOMMENT="Course:comment:";

    //分享量
    public static final String COURSESHARE="Course:share:";

    //分享用户统计
    //  key : Course:share_id:uuid;
    //  value :map==> openid:count
    public static final String COURSESHAREID="Course:share_id:";

    //ios app 分享量
    //key ： Course:share:ios:app:uuid
    //value ：count
    public static final String COURSESHAREIOSAPP="Course:share:ios:app:";

    //ios h5 分享量
    //key ： Course:share:ios:h5:uuid
    //value ：count
    public static final String GETCOURSESHAREIOSH5="Course:share:ios:h5:";

    //android app 分享量
    //key ： Course:share:android:app:uuid
    //value ：count
    public static final String COURSESHAREANDROIDAPP="Course:share:android:app:";

    //android h5 分享量
    //key ： Course:share:android:h5:uuid
    //value ：count
    public static final String COURSESHAREANDROIDH5="Course:share:android:h5:";

    //点赞量
    public static final String COURSELIKE="Course:like:";

    //点赞用户统计
    //  key : Course:like_id;
    //  value :map==> openid:count
    public static final String COURSELIKEID="Course:like_id";


    /**
     * live 直播kv end============
     */


    /**
     * 搜索记录统计 kv start
     */

    //  key : Search:course:search_userid:
    //  value :map==> keyword:count
    public static final String SEARCHCOURSESEARCHAPPID="Search:course:search_userid:";

    //  key : Search:article:search_userid:
    //  value :map==> keyword:count
    public static final String SEARCHARTICLESEARCHAPPID="Search:article:search_userid:";

    /**
     * 搜索记录统计 kv end
     */







    /**
     * 微信分享 start========
     */
    //微信公众平台基础服务access_token（每天有数量限制）
    public static final String WECHATSHAREACCESSTOKEN = "wechat:platform:share:access_token";
    //微信公众平台基础服务jsapi_ticket（每天有数量限制）
    public static final String WECHATSHAREJSAPITICKET ="wechat:platform:share:jsapi_ticket";
    /**
     * 微信分享 end========
     */


    //用户获取验证码数据
    public static final String USER_SMS_SUM = "userSMSSum";

    //用户登录状态
    public static final String USER_LOGIN = "userLogin";


}
