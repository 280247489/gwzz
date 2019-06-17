package com.memory.cms.controller;

import com.alibaba.fastjson.JSON;
import com.memory.cms.service.CourseMemoryService;
import com.memory.cms.service.LiveMasterCmsService;
import com.memory.cms.service.LiveSlaveCmsService;
import com.memory.common.async.DemoAsyncTask;
import com.memory.common.utils.FileUtils;
import com.memory.common.utils.Result;
import com.memory.common.utils.ResultUtil;
import com.memory.common.utils.Utils;
import com.memory.entity.bean.Ext;
import com.memory.entity.bean.ExtModel;
import com.memory.entity.bean.MasterModel;
import com.memory.entity.jpa.LiveMaster;
import com.memory.entity.jpa.LiveSlave;
import com.memory.redis.config.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static com.memory.redis.CacheConstantConfig.SHARECOURSECONTENT;

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



    /**
     * 直播列表查询
     * @return
     */
    @RequestMapping("list")
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
            Map<String,Object> returnMap = new HashMap<String, Object>();
            String uuid = Utils.getShortUUTimeStamp();
            com.memory.entity.bean.LiveMaster liveMaster = masterModel.getLiveMaster();

            if(extModel == null || extModel.getExtList() == null || masterModel == null ){
                result = ResultUtil.error(-1,"extList or liveMaster is null");
                return result;
            }

            List<LiveSlave>  slaveList = dealData(extModel, uuid, liveMaster.getOperatorId());

            LiveMaster master = initMaster(uuid, liveMaster);

            LiveMaster returnMaster =liveMasterCmsService.add(master);

            List<LiveSlave> returnList = liveSlaveCmsService.saveAll(slaveList);

            if(returnMaster != null && returnList !=null){
                returnMap.put("master",returnMaster);
                returnMap.put("slave",returnList);
                //存储到redis 临时
                redisLive2NoExist(uuid);

                asyncDownloadFromXiaoZhuShou(slaveList);

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
    public Result update(ExtModel extModel,MasterModel masterModel){
        Result result = new Result();
        try {

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

            List<LiveSlave>  slaveList = dealData(extModel, uuid, liveMaster.getOperatorId());


            List<LiveSlave> removeList = liveSlaveCmsService.queryLiveSlaveByLiveMasterId(liveMaster.getId());

            List<LiveSlave> returnList =  liveSlaveCmsService.deleteAndSave(removeList,slaveList);

            if(returnList != null){
                returnMap.put("master",master);
                returnMap.put("slave",returnList);

                upgradeLiveDb2Redis(uuid,true);

                asyncDownloadFromXiaoZhuShou(returnList);
            }

            result = ResultUtil.success(returnMap);
        }catch (Exception e){
            e.printStackTrace();
            log.error("liveMaster/cms/update",e.getMessage());
        }
        return result;
    }



    /**
     * 变更直播状态
     * @param status
     * @param id
     * @return
     */
    @RequestMapping("status")
    public Result changeLiveStatus(@RequestParam("status") Integer status,@RequestParam("id") String id){
        Result result = new Result();
        try {
            System.out.println("id === " + id);
            if(!checkLiveMaster(id)){
                return ResultUtil.error(-1,"非法请求.当前课程不存在！");
            }

              int count = liveMasterCmsService.updateLiveMasterStatus(status,id);
              if(count > 0){
                  result = ResultUtil.success("变更状态成功!");
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
     * @param online
     * @param id
     * @return
     */
    @RequestMapping("online")
    public Result changeOnline(@RequestParam("online") Integer online,@RequestParam("id") String id){
        Result result = new Result();
        try {

            if(!checkLiveMaster(id)){
                return ResultUtil.error(-1,"非法请求.当前课程不存在！");
            }
            liveMasterCmsService.updateLiveMasterOnline(online,id);

            //下线
            if(online == 0){
                redisLive2NoExist(id);
            }else{
                //上线状态，同步db2redis
                upgradeLiveDb2Redis(id,false);
            }
            result = ResultUtil.success("变更状态成功!");
        }catch (Exception e){
            e.printStackTrace();
            log.error("liveMaster/cms/online",e.getMessage());
        }
        return result;
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
            log.error("route",e.getMessage());
        }
        return result;
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
        // master.setLiveMasterSynthesisAudioUrl("");
        //上下架状态(0下架 1 上架)'
        master.setLiveMasterIsOnline(0);
        //是否关联课程(0未关联，1已关联）
        master.setLiveMasterIsRelation(0);
        //是否推送(0未推送，1已推送)
        master.setLiveMasterIsPush(0);
        master.setLiveMasterStarttime(liveMaster.getStartTime());
        master.setLiveMasterEndtime(liveMaster.getEndTime());
        master.setLiveMasterCreateTime(new Date());
        master.setLiveMasterCreateId(liveMaster.getOperatorId());
        master.setLiveMasterUpdateTime(new Date());
        master.setLiveMasterUpdateId(liveMaster.getOperatorId());
        return master;
    }

    private List<LiveSlave>  dealData(ExtModel extModel, String uuid, String operatorId) {
        List<Ext> extList =   extModel.getExtList();
        int sort = 0;
        List<LiveSlave> slaveList = new ArrayList<LiveSlave>();


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
                audioUrl = uploadAudio(uuid, sort, ext);

                //图片
            }else if(type == 3){
                 imgUrl = uploadImg(uuid, sort, ext);
            }
            initSlave(uuid, operatorId, sort, slaveList, nickname, logo, words, imgUrl, audioUrl, audioTime, type);

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
        if(!imgFile.isEmpty() ){
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
        if(!audioFile.isEmpty()){

                String prefix = sort + "";
                String fileName = FileUtils.getAudioFileName(prefix,audioFile);
                String customCmsPath = FileUtils.getCustomCmsPath("live",uuid);
                audioUrl =  FileUtils.upload(audioFile,FileUtils.getLocalPath(),customCmsPath,fileName);



        }else {
              audioUrl = ext.getAudioUrl();
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

    private void redisLive2NoExist(String uuid) {
        String keyHash = SHARECOURSECONTENT + uuid;
        redisUtil.hset(keyHash, "master", "notExist");
        redisUtil.hset(keyHash, "slave", JSON.toJSONString("notExist"));
    }

    private void  upgradeLiveDb2Redis(String masterId,boolean isAddMemory){
        com.memory.entity.bean.LiveSlave liveSlave = new  com.memory.entity.bean.LiveSlave();
        String keyHash = SHARECOURSECONTENT + masterId;
        List<com.memory.entity.bean.LiveSlave> list = liveSlaveCmsService.queryLiveSlaveList(masterId);
        List<Map<String,Object>> showList = liveSlave.refactorData(list);
        LiveMaster master = liveMasterCmsService.getLiveMasterById(masterId);
        redisUtil.hset(keyHash, "master", master.getLiveMasterName());
        redisUtil.hset(keyHash, "slave", JSON.toJSONString(showList));

        if(isAddMemory){
            Object obj = courseMemoryService.getLiveSlaveById(masterId);
            if(obj != null){
                courseMemoryService.addLiveMemory(masterId);
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



}
