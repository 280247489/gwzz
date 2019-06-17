package com.memory.common.async;

import com.memory.cms.service.CourseExtCmsService;
import com.memory.cms.service.LiveSlaveCmsService;
import com.memory.common.utils.FileUtils;
import com.memory.common.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * @Auther: cui.Memory
 * @Date: 2018/11/5 0005 13:55
 * @Description: 异步任务类
 * //开启异步任务类
 * SpringBoot启动类中添加 @EnableAsync
 */
@Component
public class DemoAsyncTask {

    @Autowired
    private LiveSlaveCmsService liveSlaveCmsService;


    @Async
    public Future<Boolean> doTask_one() throws Exception{
        long start = System.currentTimeMillis();
        Thread.sleep(1000);
        long end = System.currentTimeMillis();
        System.out.println("doTask_one: " + (end - start) + "毫秒");
        return new AsyncResult<>(true);
    }

    @Async
    public Future<Boolean> doTask_two() throws Exception{
        long start = System.currentTimeMillis();
        Thread.sleep(2000);
        long end = System.currentTimeMillis();
        System.out.println("doTask_two: " + (end - start) + "毫秒");

        return new AsyncResult<>(true);
    }

    @Async
    public Future<Boolean> doTask_three() throws Exception{
        long start = System.currentTimeMillis();
        Thread.sleep(3000);
        long end = System.currentTimeMillis();
        System.out.println("doTask_three: " + (end - start) + "毫秒");

        return new AsyncResult<>(true);
    }


    @Async
    public Future<Boolean> doTask_fileDownload(String url,String fileName,String path) throws Exception{
        long start = System.currentTimeMillis();
        FileUtils.downLoadFromUrl(url,fileName,path);
        long end = System.currentTimeMillis();
        System.out.println("doTask_one: " + (end - start) + "毫秒");
        return new AsyncResult<>(true);
    }

    @Async
    public Future<Boolean> doTask_fileSyncDownload(String url,String fileName,String path,String masterId,String sort,int fileType) throws Exception{
        long start = System.currentTimeMillis();
        String showPath ="";
        path = path + "/" + masterId;
        //校验资源链接是否有效
        if(Utils.isHttpAccess(url)){
          Boolean isTrue =  FileUtils.downLoadFromUrl(url,fileName,path);
          //文件下载成功变更路径地址，文件下载成功后更新db
          if(isTrue){
              showPath = path + "/" +fileName;
              //语音
              if(fileType ==2){
                  liveSlaveCmsService.setLiveSlaveStaticPathByMasterIdAndLiveSlaveSort(masterId,sort,null,showPath);
                  //图片
              }else{
                  liveSlaveCmsService.setLiveSlaveStaticPathByMasterIdAndLiveSlaveSort(masterId,sort,showPath,null);
              }
          }

        }


        long end = System.currentTimeMillis();
        System.out.println("doTask_fileSyncDownload: " + (end - start) + "毫秒");
        return new AsyncResult<>(true);
    }

    /*//执行异步任务方法
    @Autowired
    private DemoAsyncTask asyncTask;
    public String startAsyncTask() throws  Exception{
        long start = System.currentTimeMillis();

        Future<Boolean> one = asyncTask.doTask_one();
        Future<Boolean> two = asyncTask.doTask_two();
        Future<Boolean> three = asyncTask.doTask_three();

        while (!one.isDone() || !two.isDone() || !three.isDone()){
            if(one.isDone() && two.isDone() && three.isDone()){
                break;
            }
        }

        long end = System.currentTimeMillis();
        String times = "任务类执行任务总时长: " + (end - start) + "毫秒";
        System.out.println(times);
        return times;
    }*/
}
