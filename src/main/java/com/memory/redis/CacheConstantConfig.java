package com.memory.redis;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: cui.Memory
 * @Date: 2019/3/28 0028 15:28
 * @Description:
 */
public class CacheConstantConfig {

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

    /**
     * article 文章kv start============
     */

    //阅读量============================================
    //阅读量 (后台操作添加的阅读量)
    //key ：  Article:view:manager:uuid
    //value ：count
    public static final String ARTICLEVIEWMANAGER = "Article:view:manager";

    //阅读量 实际有多少个用户看过
    // uuid : articleId
    //  key :Article:view_id:uuid;
    //  value :map==> appid/openid/noExist:count
    public static final String ARTICLEVIEWID = "Article:view_id:";

    //实际阅读量（真实用户的阅读量）
    //key ： Article:view:real
    //value ：count
    public static final String ARTICLEVIEW = "Article:view:";


    //ios app 内部实际阅读量（真实用户的阅读量）
    //uuid  :  articleId
    //key ： Article:view:ios:in:uuid
    //value ：count
    public static final String ARTICLEVIEWIOSIN = "Article:view:ios:in:";


    //ios app 外部实际阅读量（真实用户的阅读量）
    //uuid:articleId
    //key ： Article:view:ios:out:uuid
    //value ：count
    public static final String ARTICLEVIEWIOSOUT = "Article:view:ios:out:";



    //android app 内部实际阅读量（真实用户的阅读量）
    //uuid:articleId
    //key ： Article:view:android:in:uuid
    //value ：count
    public static final String ARTICLEVIEWANDROIDIN = "Article:view:android:in:";


    //android app 外部实际阅读量（真实用户的阅读量）
    //uuid:articleId
    //key ： Article:view:android:out:uuid
    //value ：count
    public static final String ARTICLEVIEWANDROIDOUT = "Article:view:android:out:";


    //分享次数============================================
    //分享次数(总的)
    //uuid:articleId
    //key ：  Article:share: uuid
    //value ：count
    public static final String ARTICLESHARE = "Article:share:";

    //分享用户统计去重 （以人为单位）
    // uuid ：articleId
    //  key : Article:share_id:uuid;
    //  value :map==> appid:count
    public static final String ARTICLESHAREID="Article:share_id:";


    //ios分享次数
    //uuid:articleId
    //key ： Article:share:ios: uuid
    //value ：count
    public static final String ARTICLESHAREIOS = "Article:share:ios:";

    //android分享次数
    //uuid:articleId
    //key ： Article:share:android: uuid
    //value ：count
    public static final String ARTICLESHAREANDROID = "Article:share:android:";


    //点赞次数============================================
    //点赞次数(总的)
    //uuid:articleId
    //key ： Article:like: uuid
    //value ：count
    public static final String ARTICLELIKE = "Article:like:";

    //点赞数量 详情
    //注意： 是以 用户为单位，map key 是 articleId，value 是 1/0(点赞/取消点赞)
    // uuid:appid
    //  key : Article:like:detail:uuid
    //  value :map==> articleId: int 1/0
    public static final String ARTICLELIKEDETAIL = "Article:like:detail:";

    //评论点赞============================================
    //评论点赞列表 以文章为纬度
    //uuid : articleId
    //  key :Article:comment_like:uuid
    //  value :map==> commentId: count
    public static final String ARTICLECOMMENTLIKE = "Article:comment_like:";

    //评论点赞详情
    //注意： 是以 用户为单位，map key 是 articleId，value 是 1/0(点赞/取消点赞)
    // uuid:appid
    //  key : Article:comment_like:detail:uuid
    //  value :map==> comment_like: int 1/0
    public static final String ARTICLECOMMENTLIKEDEATIL = "Article:comment_like:detail:";

    /**
     * article 文章kv end============
     */

}
