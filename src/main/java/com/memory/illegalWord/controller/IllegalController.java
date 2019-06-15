package com.memory.illegalWord.controller;
import com.memory.common.utils.BadWordUtil;
import com.memory.common.utils.FileUtils;
import com.memory.common.utils.Result;
import com.memory.common.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author INS6+
 * @date 2019/6/15 17:28
 */
@RestController
@RequestMapping("Illegal")
public class IllegalController {

    private static final Logger log = LoggerFactory.getLogger(IllegalController.class);

    //获取违禁词目录文件列表
    @RequestMapping("options")
    public Result options(){
        Result result = new Result();
        try {
            Map<String,Object> map = null;
            List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
            ArrayList<File> fileArrayList = FileUtils.getFiles(BadWordUtil.CHECKPATH);
            for (File file : fileArrayList) {
                map = new HashMap<String, Object>();
                map.put("name",file.getName());
                map.put("path",file.getPath());
                list.add(map);
            }
            result = ResultUtil.success(list);
        }catch (Exception e){
            e.printStackTrace();
            log.error("route",e.getMessage());
        }
        return result;
    }

    //更新违禁词
    @RequestMapping("upgrade")
    public Result upgrade(){
        Result result = new Result();
        try {
            //0.copy原文件到temp或者bak目录下（留一次备份数据）

            //1.上传文件到指定目录，覆盖原违禁词文件

            //2.重新调用初始化方法，对内存中的违禁词进行同步
            BadWordUtil.initWords();
            result = ResultUtil.success("更新违禁词成功");
        }catch (Exception e){
            e.printStackTrace();
            log.error("route",e.getMessage());
        }
        return result;
    }



}
