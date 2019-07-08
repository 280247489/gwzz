package com.memory.cms.controller;

import com.alibaba.fastjson.JSON;
import com.memory.cms.service.CourseMemoryService;
import com.memory.cms.service.LiveMasterCmsService;
import com.memory.cms.service.LiveSlaveCmsService;
import com.memory.common.async.DemoAsyncTask;
import com.memory.common.utils.*;
import com.memory.entity.bean.*;
import com.memory.entity.jpa.LiveMaster;
import com.memory.entity.jpa.LiveSlave;
import com.memory.redis.config.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

import static com.memory.redis.CacheConstantConfig.*;

/**
 * @author INS6+
 * @date 2019/6/6 15:52
 */
@RestController
@RequestMapping("liveMaster/cms")
public class LiveMasterCmsController {

    private static final Logger log = LoggerFactory.getLogger(CourseTypeCmsController.class);


    @Autowired
    private LiveMasterCmsService liveMasterCmsService;

    @Autowired
    private LiveSlaveCmsService liveSlaveCmsService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private DemoAsyncTask task;


    @Autowired
    private CourseMemoryService courseMemoryService;

    private final String pwd="memory";



    @RequestMapping("list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,
                       @RequestParam("live_master_name") String live_master_name, @RequestParam("operator_id") String operator_id,
                       @RequestParam("status") Integer status){
        Result result = new Result();
        try {
            int pageIndex = page+1;
            int limit = size;
            List<LiveMaster> list = liveMasterCmsService.queryLiveMasterByQueHql(pageIndex,limit,live_master_name,operator_id,status);
            int totalElements = liveMasterCmsService.queryLiveMasterByQueHqlCount(live_master_name,operator_id,status);
            PageResult pageResult = PageResult.getPageResult(page, size, list, totalElements);
            result = ResultUtil.success(pageResult);
        }catch (Exception e){
            e.printStackTrace();
            log.error("liveMaster/cms/list",e);
        }
        return result;
    }

    /**
     * 直播列表查询
     * @return
     */
    @RequestMapping("list2")
    public Result queryLiveMasterListOrderByUpdateTimeDesc(){
        Result result = new Result();
        try {
            List<LiveMaster> masterList = liveMasterCmsService.queryLiveMasterList();
            result = ResultUtil.success(masterList);

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 添加直播
     * @param extModel
     * @param masterModel
     * @return
     */
    @RequestMapping("add")
    @ResponseBody
    public Result add(ExtModel extModel,MasterModel masterModel){
        Result result = new Result();
        try {
            //live_master_endtime
            Map<String,Object> returnMap = new HashMap<String, Object>();
            String uuid = Utils.getShortUUTimeStamp();
            com.memory.entity.bean.LiveMaster liveMaster = masterModel.getLiveMaster();

            if(extModel == null || extModel.getExtList() == null || masterModel == null ){
                result = ResultUtil.error(-1,"extList or liveMaster is null");
                return result;
            }

            List<LiveSlave>  slaveList = dealData(extModel, uuid, liveMaster.getOperatorId(),false,null);

            LiveMaster master = initMaster(uuid, liveMaster);

            LiveMaster returnMaster =liveMasterCmsService.add(master);

            List<LiveSlave> returnList = liveSlaveCmsService.saveAll(slaveList);

            if(returnMaster != null && returnList !=null){
                returnMap.put("master",returnMaster);
                returnMap.put("slave",returnList);
                //存储到redis 临时
                liveMasterCmsService.redisLive2NoExist(uuid);

               // asyncDownloadFromXiaoZhuShou(slaveList);

            }

            result = ResultUtil.success(returnMap);
        }catch (Exception e){
            e.printStackTrace();
            log.error("liveMaster/cms/add",e);
        }
        return result;
    }


    @RequestMapping("update")
    @ResponseBody
    public Result update(ExtModel extModel, MasterModel masterModel, RemoveModel removeModel){
        Result result = new Result();
        try {
            System.out.println("removeModel === " +JSON.toJSONString(removeModel));

            if(extModel == null || extModel.getExtList() == null || masterModel == null ){
                result = ResultUtil.error(-1,"extList or liveMaster is null");
                return result;
            }

            Map<String,Object> returnMap = new HashMap<String, Object>();
            String uuid = masterModel.getLiveMaster().getId();
            com.memory.entity.bean.LiveMaster liveMaster = masterModel.getLiveMaster();
            LiveMaster master = liveMasterCmsService.getLiveMasterById(uuid);
            if(master == null){
                return ResultUtil.error(-1,"非法直播!");
            }

            List<LiveSlave>  slaveList = dealData(extModel, uuid, liveMaster.getOperatorId(),true,removeModel);

            master.setLiveMasterName(masterModel.getLiveMaster().getLiveMasterName());
            master.setLiveMasterDescribe(masterModel.getLiveMaster().getLiveMasterDescribe());
            master.setLiveMasterUpdateTime(new Date());
            master.setLiveMasterUpdateId(masterModel.getLiveMaster().getOperatorId());
            LiveMaster  returnMaster = liveMasterCmsService.update(master);

            List<LiveSlave> removeList = liveSlaveCmsService.queryLiveSlaveByLiveMasterId(liveMaster.getId());

            List<LiveSlave> returnList =  liveSlaveCmsService.deleteAndSave(removeList,slaveList);

            if(returnMaster !=null && returnList != null){
                returnMap.put("master",returnMaster);
                returnMap.put("slave",returnList);
                liveMasterCmsService.upgradeLiveDb2Redis(uuid,true);

                //asyncDownloadFromXiaoZhuShou(returnList);
                result = ResultUtil.success(returnMap);
            }else {
                result = ResultUtil.error(-1,"修改直播失败");
            }


        }catch (Exception e){
            e.printStackTrace();
            log.error("liveMaster/cms/update",e.getMessage());
        }
        return result;
    }



    /**
     * 变更直播状态
     * @param id
     * @return
     */
    @RequestMapping("status")
    public Result changeLiveStatus(@RequestParam("id") String id,@RequestParam("operator_id") String operator_id){
        Result result = new Result();
        try {
            LiveMaster master=  liveMasterCmsService.getLiveMasterById(id);
            if(master ==null){
                return ResultUtil.error(-1,"非法请求.当前课程不存在！");
            }
            int nowStatus = master.getLiveMasterStatus();

            if(nowStatus == 2){
                return ResultUtil.error(-1,"fail","直播已关闭!不能修改");
            }

            nowStatus +=1;

            master.setLiveMasterStatus(nowStatus);
            master.setLiveMasterUpdateTime(new Date());
            master.setLiveMasterUpdateId(operator_id);

          if(nowStatus == 1){
              liveMasterCmsService.changeAllStatus2close(id);
          }

            LiveMaster returnMaster = liveMasterCmsService.update(master);

              if(returnMaster != null){
                  result = ResultUtil.success(returnMaster.getLiveMasterStatus());
              }else {
                  result =ResultUtil.error(-1,"变更状态失败");
              }
        }catch (Exception e){
            e.printStackTrace();
            log.error("liveMaster/cms/status",e.getMessage());
        }
        return result;
    }


    /**
     * 变更直播上下线
     * @param id
     * @return
     */
    @RequestMapping("online")
    public Result changeOnline(@RequestParam("id") String id,@RequestParam("operator_id") String operator_id){
        Result result = new Result();
        try {
            int online = 0;
            LiveMaster master=  liveMasterCmsService.getLiveMasterById(id);
            if(master ==null){
                return ResultUtil.error(-1,"非法请求.当前课程不存在！");
            }

            int oldOnline = master.getLiveMasterIsOnline();
            if(oldOnline == 0){
                online=1;
            }
            master.setLiveMasterIsOnline(online);
            master.setLiveMasterUpdateTime(new Date());
            master.setLiveMasterUpdateId(operator_id);

            LiveMaster returnMaster = liveMasterCmsService.update(master);

            if (returnMaster == null){
                return ResultUtil.error(-1,"变更状态失败");
            }

            //下线
            syncOnline2Redis(id, online);
            result = ResultUtil.success(online);
        }catch (Exception e){
            e.printStackTrace();
            log.error("liveMaster/cms/online",e.getMessage());
        }
        return result;
    }

    private void syncOnline2Redis( String id, int online) {
        if(online == 0){
            liveMasterCmsService.redisLive2NoExist(id);
        }else{
            //上线状态，同步db2redis
            liveMasterCmsService.upgradeLiveDb2Redis(id,false);
        }
    }


    /**
     * 关联课程
     * @return
     */
    @RequestMapping("relationChange")
    public Result relationCourse(@RequestParam("id") String id,@RequestParam("courseId") String courseId){
        Result result = new Result();
        try {
            if(!checkLiveMaster(id)){
                return ResultUtil.error(-1,"非法课程");
            }

            int count = liveMasterCmsService.upgradeLiveMasterIsRelation(1,courseId,id);
            if(count > 0){
                result = ResultUtil.success("课程关联直播成功!");
            }else {
                result = ResultUtil.error(-1,"课程关联直播失败!");
            }

        }catch (Exception e){
            e.printStackTrace();
            log.error("liveMaster/cms/relation",e.getMessage());
        }
        return result;
    }

    /**
     * 合成音频
      * @return
     */
    @RequestMapping("synthesisAudio")
    public Result synthesisAudio(@RequestParam("id") String id){
        Result result = new Result();
        try {


            result = ResultUtil.success();
        }catch (Exception e){
            e.printStackTrace();
            log.error("liveMaster/cms/synthesisAudio",e.getMessage());
        }
        return result;
    }


    /**
     * 查询可关联直播列表
     * @return
     */
    @RequestMapping("relationList")
    public Result relationList(){
        Result result = new Result();
        try {
            List<Map<String,Object>> returnList = new ArrayList<Map<String, Object>>();
            Map<String,Object> map = null;

            List<LiveMaster> list = liveMasterCmsService.queryLiveMasterByLiveMasterIsSynthesisAudio();
            for (LiveMaster master : list) {
                map = new HashMap<String, Object>();
                map.put("id",master.getId());
                map.put("name",master.getLiveMasterName());
            }

            returnList.add(map);
            result = ResultUtil.success(returnList);
        }catch (Exception e){
            e.printStackTrace();
            log.error("liveMaster/cms/relationList",e.getMessage());
        }
        return result;
    }


    @RequestMapping("detail")
    public Result getDetailById(@RequestParam("id") String id){
        Result result = new Result();
        try {
            Map<String,Object> map =new HashMap<String, Object>();
            LiveMaster master=  liveMasterCmsService.getLiveMasterById(id);
            if(master == null){
                return ResultUtil.error(-1,"非法直播!");
            }

            List<com.memory.entity.bean.LiveSlave> list= liveSlaveCmsService.queryLiveSlaveList(id);
            map.put("master",master);
            map.put("slave",list);
            result = ResultUtil.success(map);
        }catch (Exception e){
            e.printStackTrace();
            log.error("liveMaster/cms/detail",e.getMessage());
        }
        return result;
    }

    /**
     * 直播查询接口
     * @param id    直播id
     * @param openId  微信openId 或 appId
     * @param terminal 终端  0 ：app 或  1 ：h5
     * @param os  操作系统 0：ios 或 1：android
     * @return
     */
    @RequestMapping("live")
    public Result getLiveById(@RequestParam("id") String id,@RequestParam("openId") String openId,Integer terminal,Integer os){
        Result result = new Result();
        try {

            String keyCourseView = COURSEVIEW + id;
            String keyCourseViewOs ="";
            String keyCourseViewComment = COURSECOMMENT + id;
            String keyCourseViewId= COURSEVIEWID +id;

            // ios
            if(os == 0){
                //app
                if(terminal == 0){
                    keyCourseViewOs = COURSEVIEWIOSAPP + id;
                //h5
                }else {
                    keyCourseViewOs = COURSEVIEWIOSH5 +id;
                }
            // android
            }else {

                //app
                if(terminal == 0){
                    keyCourseViewOs = COURSEVIEWANDROIDAPP + id;
                    //h5
                }else {
                    keyCourseViewOs = COURSEVIEWANDROIDH5 +id;
                }
            }

            //内存
            if(COURSEMAP.containsKey(keyCourseViewComment)){

                total2Redis(openId, keyCourseView, keyCourseViewOs, keyCourseViewId);

                System.out.println("内存==============================="+keyCourseViewComment);
                return ResultUtil.success(COURSEMAP.get(keyCourseViewComment));

            }else {

                Object object = redisUtil.hget(keyCourseViewComment,"slave");

                //判断redis中是否含有此次课程数据
                //有课程数据
                if(object != null){
                    if(!"notExist".equals(object)){
                        Map<String,Object> map = new HashMap<>();
                        map.put("master",redisUtil.hget(keyCourseViewComment,"master"));
                        map.put("slave",JSON.parse(object.toString()));

                        total2Redis(openId, keyCourseView, keyCourseViewOs, keyCourseViewId);
                        return ResultUtil.success(map);

                    }else {
                        result.setCode(1);
                        result.setMsg("notExist");
                        return result;
                    }
                   //redis没有课程数据
                }else {

                    LiveMaster master = liveMasterCmsService.getLiveMasterById(id);
                    if(master!=null){
                        //上线
                        if(master.getLiveMasterIsOnline() ==1){
                            System.out.println("redis没有课程数据，数据库中有课程数据，数据库的课程是上线状态,同步db2redis=============================="+keyCourseViewComment);
                            List<com.memory.entity.bean.LiveSlave> list = liveSlaveCmsService.queryLiveSlaveList(id);
                            com.memory.entity.bean.LiveSlave liveSlave = new  com.memory.entity.bean.LiveSlave();
                            List<Map<String,Object>> showList = liveSlave.refactorData(list);

                            redisUtil.hset(keyCourseViewComment,"master",master.getLiveMasterName());
                            redisUtil.hset(keyCourseViewComment,"slave",showList);

                            Map<String,Object> map = new HashMap<>();
                            map.put("master",master.getLiveMasterName());
                            map.put("slave",showList);
                            total2Redis(openId, keyCourseView, keyCourseViewOs, keyCourseViewId);
                            return ResultUtil.success(map);
                        //下线
                        }else {
                            System.out.println("redis没有课程数据，数据库中有数据，但是课程是下线状态=============================="+keyCourseViewComment);
                            redisUtil.hset(keyCourseViewComment,"master","notExist");
                            redisUtil.hset(keyCourseViewComment,"slave","notExist");
                            result.setCode(1);
                            result.setMsg("notExist");
                        }
                    }else {
                        System.out.println("redis没有课程数据,数据库也没有数据=============================="+keyCourseViewComment);
                        result.setCode(1);
                        result.setMsg("notExist");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("liveMaster/cms/live",e.getMessage());
        }
        return result;
    }

    //@RequestMapping("deleteSlaveById")
    public Result deleteSlaveById(@RequestParam  String slave_id){
        Result result = new Result();
        try {
            LiveSlave slave = liveSlaveCmsService.getLiveSlaveById(slave_id);
            if(Utils.isNotNull(slave)){
                //音频
                if(slave.getLiveSlaveType()==2){
                    String audioUrl = slave.getLiveSlaveAudio();
                    System.out.println("audioUrl ====" +audioUrl);
                    //判断是否是网络资源
                    //非网络资源删除服务器上的源文件
                    if(audioUrl.indexOf("http")<1){
                        String path = FileUtils.getLocalPath()+ audioUrl;
                        String out = cmdRemoveFile(path);
                        System.out.println("out ======== "+out);
                        result = ResultUtil.success("remove path ："+path);
                    }
                }else{
                    result = ResultUtil.success("没有要移除的音频");
                }
            }else {
                result= ResultUtil.error(-1,"非法直播记录");
            }

        }catch (Exception e){
            e.printStackTrace();
            log.error("liveMaster/cms/deleteSlaveById",e.getMessage());
        }
        return result;
    }

    @RequestMapping("deleteAllSlave")
    public Result deleteAllSlave(@RequestParam  String master_id){
        Result result = new Result();
        try {
            LiveMaster master = liveMasterCmsService.getLiveMasterById(master_id);
            if(Utils.isNotNull(master)){
                String path = FileUtils.getCustomCmsPath("live",master_id);
               String out = cmdRemoveFile(path);
                System.out.println("out ======== "+out);
                result = ResultUtil.success("remove path ："+path);
            }else {
                result= ResultUtil.error(-1,"非法直播记录");
            }

        }catch (Exception e){
            e.printStackTrace();
            log.error("liveMaster/cms/deleteAllSlave",e.getMessage());
        }
        return result;
    }


    @RequestMapping("mergeMp3")
    public Result mergeMp3(@RequestParam String master_id){
        Result result = new Result();
        try {
            LiveMaster master = liveMasterCmsService.getLiveMasterById(master_id);
            if(master==null){
                return ResultUtil.error(-1,"非法直播!");
            }
            String mergePath ="";
            mergePath = FileUtils.getLocalPath() +  FileUtils.getCustomCmsPath("live",master_id);
            File folder = new File(mergePath);
            //文件目录不存在
            if (!folder.exists() && !folder.isDirectory()) {
                result = ResultUtil.error(-1,"没有要合成的音频");
                //文件目录存在
            }else{

                List<com.memory.entity.bean.LiveSlave> list = liveSlaveCmsService.queryLiveSlaveList(master_id);

                String txtFilePath =  writeFile2Txt(list,mergePath,"filelist.txt");

                String out = cmdExceMergeMp3Shell(mergePath,txtFilePath);
                System.out.println("out =================================" + out);
                if(Utils.isNotNull(out) && !"Fail".equals(out) &&  !"[sudo] password for memory: Fail".equals(out)){
                    String  downloadPath = out/*.split(":")[1]*/.trim();
                    if(downloadPath.indexOf("sudo")>= 0){
                        downloadPath = out.split(":")[1];
                    }
                    System.out.println("downloadPath 11111=========="+downloadPath);
                    downloadPath =downloadPath.substring(downloadPath.indexOf("/",2),downloadPath.length());
                    System.out.println("downloadPath 2222=========="+downloadPath);
                    downloadPath = FileUtils.getLocalShowPath() + downloadPath;
                    master.setLiveMasterIsSynthesisAudio(1);
                    master.setLiveMasterSynthesisAudioUrl(downloadPath);
                    liveMasterCmsService.update(master);
                    result = ResultUtil.success(downloadPath);
                }else {
                    result = ResultUtil.error(-1,"音频合成失败");
                }
            }

        }catch (Exception e){

            e.printStackTrace();
            log.error("liveMaster/cms/mergeMp3",e.getMessage());
        }
        return result;
    }


    public static void main(String[] args) {
        //  /test.jlgwzz.com/cms/live/kWRty0jN1561772072044/merge_20190705162751.mp3
        String downloadPath = "/test.jlgwzz.com/cms/live/kWRty0jN1561772072044/merge_20190705162751.mp3";
        downloadPath =downloadPath.substring(downloadPath.indexOf("/",2),downloadPath.length());
        System.out.println(downloadPath);
    }



    @RequestMapping("whoami")
    public Result whoami(){
        Result result = new Result();
        try {
            String out = cmdExceTestUser();
            result = ResultUtil.success(out);
        }catch (Exception e){
            e.printStackTrace();
            log.error("whoami",e.getMessage());
        }
        return result;
    }



    private void total2Redis( String openId, String keyCourseView, String keyCourseViewOs, String keyCourseViewId) {
        redisUtil.incr(keyCourseView,1);
        redisUtil.incr(keyCourseViewOs,1);
        redisUtil.hincr(keyCourseViewId,openId,1);
    }



    private LiveMaster initMaster(String uuid, com.memory.entity.bean.LiveMaster liveMaster) {
        LiveMaster master = new LiveMaster();
        master.setId(uuid);
        master.setCourseId("");
        master.setLiveMasterName(liveMaster.getLiveMasterName());
        master.setLiveMasterDescribe(liveMaster.getLiveMasterDescribe());
        //直播状态(0 未直播 1上线 2直播完毕)
        master.setLiveMasterStatus(0);
        //点赞数
        master.setLiveMasterLike(0);
        //是否合成音频 0未合成 1合成
        master.setLiveMasterIsSynthesisAudio(0);
        //音频地址
         master.setLiveMasterSynthesisAudioUrl("");
        //上下架状态(0下架 1 上架)'
        master.setLiveMasterIsOnline(0);
        //是否关联课程(0未关联，1已关联）
        master.setLiveMasterIsRelation(0);
        //是否推送(0未推送，1已推送)
        master.setLiveMasterIsPush(0);
       // master.setLiveMasterStarttime(liveMaster.getStartTime());
       // master.setLiveMasterEndtime(liveMaster.getEndTime());
        master.setLiveMasterStarttime("");
        master.setLiveMasterEndtime("");
        master.setLiveMasterCreateTime(new Date());
        master.setLiveMasterCreateId(liveMaster.getOperatorId());
        master.setLiveMasterUpdateTime(new Date());
        master.setLiveMasterUpdateId(liveMaster.getOperatorId());
        return master;
    }

    private List<LiveSlave>  dealData(ExtModel extModel, String uuid, String operatorId,Boolean isUpdate,RemoveModel removeModel) {
        List<Ext> extList =   extModel.getExtList();
        int sort = 0;
        List<LiveSlave> slaveList = new ArrayList<LiveSlave>();

        List<String> fileChangeList = new ArrayList<String>();
        for (Ext ext : extList) {
            int audioTime = 0;
            String nickname ="",logo="",words="",imgUrl="",audioUrl="";
            sort ++ ;
            int type = ext.getType();
            nickname = ext.getName();

            //文字
            if(type == 1){
                words = ext.getWords();
            //语音
            }else if(type == 2){
                audioTime = ext.getTimes();
                if(Utils.isNotNull(ext.getAudioFile())){
                    audioUrl = uploadAudio(uuid, sort, ext);
                    String changeId = uuid +"_"+sort;
                    fileChangeList.add(changeId);
                }else {
                    audioUrl = ext.getAudioUrl();
                }

                //图片
            }else if(type == 3){
                 imgUrl = uploadImg(uuid, sort, ext);
            }
            initSlave(uuid, operatorId, sort, slaveList, nickname, logo, words, imgUrl, audioUrl, audioTime, type);
        }

        if(isUpdate){
            List<LiveSlave> historySlaveList = liveSlaveCmsService.queryLiveSlaveByLiveMasterId(uuid);

            //更新删除掉多余文件
            //找出要删除的文件
            for (String changeStr : fileChangeList) {
                for (LiveSlave liveSlave : historySlaveList) {
                    if(changeStr.equals(liveSlave.getId())){
                        String path = FileUtils.getLocalPath()+liveSlave.getLiveSlaveAudio();
                        System.out.println("remove path = "+path);
                        path = path.trim().replace("/r/n","").replace("/r","");
                        FileUtils.deleteFile(path);
                        //String out=    cmdRemoveFile(path);
                       // System.out.println("remove update out is ..." +out);
                    }
                }

            }

            //删除指定文件
            if(Utils.isNotNull(removeModel) && Utils.isNotNull(removeModel.getRemoveList())){
                List<Slave> removeSlaveList =removeModel.getRemoveList();
                for (Slave slave : removeSlaveList) {
                    String slaveId = slave.getSlaveId();
                    for (LiveSlave liveSlave : historySlaveList) {
                        if(slaveId.equals(liveSlave.getId())){
                            String path = FileUtils.getLocalPath()+liveSlave.getLiveSlaveAudio();
                            System.out.println("remove ( removeModel ) path = "+path);
                            path = path.trim().replace("/r/n","").replace("/r","");
                            FileUtils.deleteFile(path);
                           // String out =cmdRemoveFile(path);
                          //  System.out.println("remove out is ..." +out);
                        }
                    }
                }
            }


        }

        return slaveList;
    }

    private void initSlave(String uuid, String operatorId, int sort, List<LiveSlave> slaveList, String nickname, String logo, String words, String imgUrl, String audioUrl, int audioTime, int type) {
        LiveSlave liveSlave = new LiveSlave();
        String slaveId = uuid + "_" + sort;
        liveSlave.setId(slaveId);
        liveSlave.setLiveMasterId(uuid);
        liveSlave.setLiveSlaveNickname(nickname);
        liveSlave.setLiveSlaveLogo(logo);
        liveSlave.setLiveSlaveType(type);
        liveSlave.setLiveSlaveWords(words);
        liveSlave.setLiveSlaveImgurl(imgUrl);
        liveSlave.setLiveSlaveAudio(audioUrl);
        liveSlave.setLiveSlaveAudioTime(audioTime);
        liveSlave.setLiveSlaveSort(sort);
        liveSlave.setLiveSlaveCreateTime(new Date());
        liveSlave.setLiveSlaveCreateId(operatorId);
        liveSlave.setLiveSlaveUpdateTime(new Date());
        liveSlave.setLiveSlaveUpdateId(operatorId);

        slaveList.add(liveSlave);
    }

    private String uploadImg(String uuid, int sort, Ext ext) {
        String imgUrl ="";
        MultipartFile imgFile = ext.getImgFile();
        if(Utils.isNotNull(imgFile)){
                String prefix = sort + "";
                String fileName = FileUtils.getImgFileName(prefix);
                String customCmsPath = FileUtils.getCustomCmsPath("live",uuid);
                imgUrl =  FileUtils.upload(imgFile,FileUtils.getLocalPath(),customCmsPath,fileName);

        }else {
                imgUrl = ext.getImgUrl();
        }
        return imgUrl;
    }

    private String uploadAudio(String uuid, int sort, Ext ext) {
        String audioUrl="";
        MultipartFile audioFile = ext.getAudioFile();

                String prefix = sort + "";
                String fileName = FileUtils.getAudioFileName(prefix,audioFile);
                String suffix =   fileName.substring(fileName.lastIndexOf("."));
                String customCmsPath = FileUtils.getCustomCmsPath("live",uuid);
                audioUrl =  FileUtils.upload(audioFile,FileUtils.getLocalPath(),customCmsPath,fileName);

                //.amr 文件进行自动转换mp3
                if(".amr".equals(suffix)){
                    System.out.println("suffix ========================"+suffix);
                    String localPath = FileUtils.getLocalPath();
                    //path 会返回 文件的路径或 Fail 如果是Fail 代表文件转换失败, 需手动上传
                    String path = cmdExceAmr2Mp3Shell(localPath +"/"+audioUrl );
                    path = path.trim().replace("/r/n","").replace("/r","");
                    audioUrl=  path.substring(path.indexOf("/cms"),path.length());
                }

        return audioUrl;
    }


   private void asyncDownloadFromXiaoZhuShou(List<LiveSlave> slaveListList) throws Exception{
       for (LiveSlave liveSlave : slaveListList) {
           int type = liveSlave.getLiveSlaveType();
           int sort = liveSlave.getLiveSlaveSort();
           String masterId = liveSlave.getLiveMasterId();
           String fileName ="";
           String customCmsPath = FileUtils.getCustomCmsPath("live",masterId);

           //语音
           if(type == 2){
               String audioUrl = liveSlave.getLiveSlaveAudio();
               if(audioUrl.indexOf("http") > -1){
                   String realFileName = audioUrl;
                   realFileName = realFileName.substring(realFileName.lastIndexOf("/")+1);

                   fileName = FileUtils.getAudioFileName(sort+"",realFileName);
                   task.doTask_fileSyncDownload(audioUrl,fileName,customCmsPath,masterId,sort+"",type);
               }
           }
           //图片
           if(type == 3){
               String imgUrl = liveSlave.getLiveSlaveImgurl();
               if(imgUrl.indexOf("http") > -1){
                   fileName = FileUtils.getImgFileName(sort+"");
                   task.doTask_fileSyncDownload(imgUrl,fileName,customCmsPath,masterId,sort+"",type);
               }

           }

       }
        
   }



   private Boolean checkLiveMaster(String id){
        Boolean flag = false;
       LiveMaster master=  liveMasterCmsService.getLiveMasterById(id);
        if(master != null){
            flag = true;
        }
        return flag;
   }


    /**
     *
     * @param amrPath 要转换的MP3职业路径
     * @return
     */
   private String cmdExceAmr2Mp3Shell(String amrPath) {
       String out = "";
       try {
             out = CmdExecutorUtil.builder(pwd)
                    .errRedirect(true)
                     .sudoCmd("sh /home/memory/amr2mp3/silk-v3/converter_wxAmr2mp3.sh " + amrPath + " mp3")
                    .exec();
        }catch (Exception e){
            e.printStackTrace();
           out ="Fail";
        }
       System.out.println("cmdExceAmr2Mp3Shell out :"+out);
       log.info("cmdExceAmr2Mp3Shell out :"+out);
       return out;
   }

    /**
     *
     * @param mergePath 要合并的MP3资源路径
     * @return
     */
   private String cmdExceMergeMp3Shell(String mergePath,String readTxtPath){
        String out ="";
        try {
            out = CmdExecutorUtil.builder(pwd)
                    .errRedirect(true)
                    .sudoCmd("sh /home/memory/amr2mp3/merge_mp3/merge_mp3.sh " + mergePath+"/ "+readTxtPath )
                    .exec();

        }catch (Exception e){
            e.printStackTrace();
            out ="Fail";
        }
       System.out.println("cmdExceMergeMp3Shell out :"+out);
        log.info("cmdExceMergeMp3Shell out :"+out);
        return out;
   }

   private String cmdRemoveFile(String filePath){
       String out ="";
       try {
           out = CmdExecutorUtil.builder(pwd)
                   .errRedirect(true)
                   .sudoCmd("rm -rf " +filePath)
                   .exec();
       }catch (Exception e){
           e.printStackTrace();
       }

       return out;
   }


   private String cmdExceTestUser(){
       String out ="";
       try {
           out = CmdExecutorUtil.builder(pwd)
                   .errRedirect(true)
                   .sudoCmd(" whoami")
                   .exec();

       }catch (Exception e){
           e.printStackTrace();
           out ="Fail";
       }
       System.out.println("cmdExceTestUser out :"+out);
       log.info("cmdExceTestUser out :"+out);
       return out;
   }

    /**
     *
     * @param path
     * @return
     */
    private String cmdExceCheckPathShell(String path){
        String out ="";
        try {
            out = CmdExecutorUtil.builder(pwd)
                    .errRedirect(true)
                    .sudoCmd("sh /home/memory/amr2mp3/merge_mp3/check_path.sh " + path+"/" )
                    .exec();

        }catch (Exception e){
            e.printStackTrace();
            out ="Fail";
        }
        System.out.println("cmdExceCheckPathShell out :"+out);
        log.info("cmdExceCheckPathShell out :"+out);
        return out;
    }

    private String writeFile2Txt(List<com.memory.entity.bean.LiveSlave> list,String filePath,String fileName) throws IOException{
        String txtFilePath = filePath+"/"+fileName;

        File folder = new File(txtFilePath);
        if(folder.exists()){
            FileUtils.deleteFile(txtFilePath);
        }

        File file = new File(txtFilePath);
        FileOutputStream fos = new FileOutputStream(file);
        OutputStreamWriter osw=new OutputStreamWriter(fos, "UTF-8");
        BufferedWriter bw=new BufferedWriter(osw);

        int i=0;
        for (com.memory.entity.bean.LiveSlave liveSlave : list) {
            i++;
            if(liveSlave.getLiveSlaveType() == 2 && liveSlave.getLiveSlaveAudio().trim().indexOf("http") < 0){
                StringBuffer sb =new StringBuffer();
                sb.append("file \'"+FileUtils.getLocalPath()+liveSlave.getLiveSlaveAudio().trim().replace("/r/n","").replace("/r","")+"\'");
                bw.write(sb.toString());
                if(i!=list.size()){
                    bw.newLine();
                }

            }
        }

        bw.close();
        osw.close();
        fos.close();
        return txtFilePath;
    }




}
