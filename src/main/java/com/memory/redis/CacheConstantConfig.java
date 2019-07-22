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

    //课程直播内容信息
    public static final String SHARELIVECONTENT ="share:course:content:";

    //课程直播内存信息
    public static Map<String,Object> LIVEMAP = new HashMap<String,Object>();
    /**
     * 课程直播 键值存储规范 end========
     */


    /**
     * 搜索记录统计 kv start
     */
    //课程搜索统计
    //  key : Search:course:search_userid:
    //  value :map==> keyword:count
    public static final String SEARCHCOURSESEARCHAPPID="Search:course:search_userid:";

    //课程当日搜索统计
    // key  : Search:course:yyyy-MM-dd
    //value : map==> keyword:count
    public static final String searchCourseDate =  "Search:course:";

    //文章搜索统计
    //  key : Search:article:search_userid:
    //  value :map==> keyword:count
    public static final String SEARCHARTICLESEARCHAPPID="Search:article:search_userid:";

    //文章当日搜索统计
    // key  : Search:article:yyyy-MM-dd
    //value : map==> keyword:count
    public static final String searchArticleDate = "Search:article:";

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
    public static final String ARTICLEVIEWMANAGER = "Article:view:manager:";

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


    /**
     * course 课程kv start============
     */

    //阅读量
    //uuid : courseId
    //key ：  Course:view:uuid
    //value ：count
    public static final String COURSEVIEW = "Course:view:";

    //阅读量============================================
    //阅读量 (后台操作添加的阅读量)
    //key ：  Course:view:manager:uuid
    //value ：count
    public static final String COURSEVIEWMANAGER = "Course:view:manager:";

    //阅读用户统计
    //uuid : courseId
    //  key : Course:view_id:uuid;
    //  value :map==> appid/openid:count
    public static final String COURSEVIEWID="Course:view_id:";

    //ios in 阅读量
    //uuid : courseId
    //key ： Course:view:ios:in:uuid
    //value ：count
    public static final String COURSEVIEWIOSIN="Course:view:ios:in:";

    //ios out 阅读量
    //uuid : courseId
    //key ： Course:view:ios:out:uuid
    //value ：count
    public static final String COURSEVIEWIOSOUT="Course:view:ios:out:";

    //android in 阅读量
    //uuid : courseId
    //key ： Course:view:android:in:uuid
    //value ：count
    public static final String COURSEVIEWANDRODIIN="Course:view:android:in:";

    //android out 阅读量
    //uuid : courseId
    //key ： Course:view:android:out:uuid
    //value ：count
    public static final String COURSEVIEWANDROIDOUT="Course:view:android:out:";

    //分享次数============================================
    //分享次数(总的)
    //uuid:courseId
    //key ：  Course:share: uuid
    //value ：count
    public static final String COURSESHARE = "Course:share:";

    //分享用户统计去重 （以人为单位）
    // uuid ：courseId
    //  key : Course:share_id:uuid;
    //  value :map==> appid:count
    public static final String COURSESHAREID="Course:share_id:";

    //ios分享次数
    //uuid:courseId
    //key ：Course:share:ios: uuid
    //value ：count
    public static final String COURSESHAREIOS = "Course:share:ios:";

    //android分享次数
    //uuid:courseId
    //key ： Course:share:android: uuid
    //value ：count
    public static final String COURSESHAREANDROID = "Course:share:android:";


    //点赞次数============================================
    //点赞次数(总的)
    //uuid:courseID
    //key ： Course:like: uuid
    //value ：count
    public static final String COURSELIKE = "Course:like:";

    //点赞数量 详情
    //注意： 是以 用户为单位，map key 是 articleId，value 是 1/0(点赞/取消点赞)
    // uuid:appid
    //  key : Course:like:detail:uuid
    //  value :map==> courseId: int 1/0
    public static final String COURSELIKEDETAIL = "Course:like:detail:";

    //评论点赞============================================
    //评论点赞列表 以文章为纬度
    //uuid : courseId
    //  key :Course:comment_like:uuid
    //  value :map==> commentId: count
    public static final String COURSECOMMENTLIKE = "Course:comment_like:";

    //评论点赞详情
    //注意： 是以 用户为单位，map key 是 articleId，value 是 1/0(点赞/取消点赞)
    // uuid:appid
    //  key : Course:comment_like:detail:uuid
    //  value :map==> comment_like: int 1/0
    public static final String COURSECOMMENTLIKEDETAIL = "Course:comment_like:detail:";

    /**
     * course 课程kv end============
     */


    /**
     * album 专辑ke start===========
     */

    //阅读量============================================
    //真实阅读量
    //uuid : albumId
    //key ：  Album:view:uuid
    //value ：count
    public static final String ALBUMVIEW = "Album:view:";

    //阅读量 (后台操作添加的阅读量)
    //key ：  Album:view:manager:uuid
    //value ：count
    public static final String ALBUMVIEWMANAGER = "Album:view:manager:";

    //阅读量 (用户去重)
    // uuid ：albumId
    //  key : Album:view_id:uuid;
    //  value :map==> appid:count
    public static final String ALBUMVIEWID="Album:view_id:";

    /**
     * album 专辑ke end=============
     */



    /**
     * live 直播kv start============
     */

    //阅读量
    //key ：  Live:view:uuid
    //value ：count
    public static final String LIVEVIEW = "Live:view:";

    //阅读用户统计
    //  key : Live:view_id:uuid;
    //  value :map==> openid:count
    public static final String LIVEVIEWID="Live:view_id:";

    //ios in 阅读量
    //key ： Live:view:ios:in:uuid
    //value ：count
    public static final String LIVEVIEWIOSIN="Live:view:ios:in:";

    //ios h5 阅读量
    //key ： Live:view:ios:out:uuid
    //value ：count
    public static final String LIVEVIEWIOSOUT="Live:view:ios:out:";

    //android in 阅读量
    //key ： Live:view:android:in:uuid
    //value ：count
    public static final String LIVEVIEWANDROIDIN="Live:view:android:in:";

    //android out 阅读量
    //key ： Live:view:android:out:uuid
    //value ：count
    public static final String LIVEVIEWANDROIDOUT="Live:view:android:out:";

    //直播内容
    public static final String LIVECOMMENT="Live:comment:";

    //分享量
    public static final String LIVESHARE="Live:share:";

    //分享用户统计
    //  key : Live:share_id:uuid;
    //  value :map==> openid:count
    public static final String LIVESHAREID="Live:share_id:";

    //ios in 分享量
    //key ： Live:share:ios:in:uuid
    //value ：count
    public static final String LIVESHAREIOSIN="Live:share:ios:in:";

    //ios out 分享量
    //key ： Live:share:ios:out:uuid
    //value ：count
    public static final String LIVESHAREIOSOUT="Live:share:ios:out:";

    //android in 分享量
    //key ： Live:share:android:in:uuid
    //value ：count
    public static final String LIVESHAREANDROIDIN="Live:share:android:in:";

    //android out 分享量
    //key ： Live:share:android:out:uuid
    //value ：count
    public static final String LIVESHAREANDROIDOUT="Live:share:android:out:";

    /**
     * live 直播kv end============
     */


    /**
     *  VV （访问网站的次数）kv start============
     */
    //ios 日活
    // Date:"yyyy-MM-dd"
    //  key : vv:ios:Date
    //  value :map==> appid: count
    public static final String VVIOS = "vv:ios:";

    // android 日活
    // Date:"yyyy-MM-dd"
    //  key : vv:ios:Date
    //  value :map==> appid: count
    public static final String VVANDROID = "vv:android:";

    /**
     *  VV （访问网站的次数）kv end============
     */









}
