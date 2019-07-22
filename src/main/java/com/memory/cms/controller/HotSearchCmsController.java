package com.memory.cms.controller;
import com.memory.cms.service.HotSearchCmsService;
import com.memory.common.utils.Result;
import com.memory.common.utils.ResultUtil;
import com.memory.common.utils.Utils;
import com.memory.entity.jpa.HotSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
import java.util.List;

/**
 * @author INS6+
 * @date 2019/7/22 13:11
 */
@RestController
@RequestMapping("hotSearch/cms")
public class HotSearchCmsController {
    private static final Logger log = LoggerFactory.getLogger(HotSearchCmsController.class);

    @Autowired
    private HotSearchCmsService hotSearchCmsService;

    //热门搜索添加
    @RequestMapping("add")
    public Result add(@ModelAttribute com.memory.entity.bean.HotSearch hotSearch){
        Result result = new Result();
        try {
            List<HotSearch> hotSearchList = hotSearchCmsService.getHotSearchByKeyWord(hotSearch.getKeyWord().trim());
            if(hotSearchList.size()>0){
                result = ResultUtil.error(-1,"搜索记录已存在，不可重复添加！");
            }else {

                HotSearch hotSearch1 = new HotSearch();
                hotSearch1.setId(Utils.getShortUUTimeStamp());
                hotSearch1.setSearchType(hotSearch.getSearchType());
                hotSearch1.setStatus(hotSearch.getStatus());
                hotSearch1.setSort(hotSearch.getSort());
                hotSearch1.setKeyWord(hotSearch.getKeyWord().trim());
                hotSearch1.setOperatorId(hotSearch.getOperatorId());
                hotSearch1.setOperatorName(hotSearch.getOperatorName());
                hotSearch1.setLastUpdateTime(new Date());
                HotSearch returnHotSearch =hotSearchCmsService.add(hotSearch1);

                result = ResultUtil.success(returnHotSearch);
            }

        }catch (Exception e){
            e.printStackTrace();
            log.error("add",e.getMessage());
        }
        return result;
    }


    //热门搜索添加
    @RequestMapping("update")
    public Result update(@ModelAttribute com.memory.entity.bean.HotSearch hotSearch){
        Result result = new Result();
        try {
            HotSearch hotSearch1 = hotSearchCmsService.getHotSearchById(hotSearch.getId());
            if(hotSearch1 == null){
                result = ResultUtil.error(-1,"非法搜索id");
            }else{
                if(Utils.isNotNull(hotSearch.getSort())){
                    hotSearch1.setSort(hotSearch.getSort());
                }
                if(Utils.isNotNull(hotSearch.getKeyWord().trim())){
                    hotSearch1.setKeyWord(hotSearch.getKeyWord().trim());
                }

                hotSearch1.setOperatorId(hotSearch.getOperatorId());
                if(Utils.isNotNull(hotSearch.getOperatorName())){
                    hotSearch1.setOperatorName(hotSearch.getOperatorName());
                }
                hotSearch1.setLastUpdateTime(new Date());
                HotSearch returnHotSearch =hotSearchCmsService.update(hotSearch1);
                result = ResultUtil.success(returnHotSearch);
            }

        }catch (Exception e){
            e.printStackTrace();
            log.error("update",e.getMessage());
        }
        return result;
    }

    //删除热门搜索记录
    @RequestMapping("remove")
    public Result remove(@ModelAttribute com.memory.entity.bean.HotSearch hotSearch){
        Result result = new Result();
        try {
            HotSearch hotSearch1 = hotSearchCmsService.getHotSearchById(hotSearch.getId());
            if(hotSearch1 == null){
                result = ResultUtil.error(-1,"非法搜索id");
            }else{
                hotSearchCmsService.delete((hotSearch.getId()));
                result = ResultUtil.success();
            }


        }catch (Exception e){
            e.printStackTrace();
            log.error("remove",e.getMessage());
        }
        return result;
    }

    //变更上下线状态
    @RequestMapping("online")
    public Result online(@ModelAttribute com.memory.entity.bean.HotSearch hotSearch){
        Result result = new Result();
        try {
            int status = 0;
            HotSearch hotSearch1 = hotSearchCmsService.getHotSearchById(hotSearch.getId());
            if(hotSearch1 == null){
                result = ResultUtil.error(-1,"非法搜索id");
            }else {

                if(hotSearch1.getStatus() ==0){
                    status =1;
                    hotSearch1.setStatus(status);
                }else {
                    hotSearch1.setStatus(status);
                }

                hotSearchCmsService.update(hotSearch1);

                result = ResultUtil.success(status);
            }

        }catch (Exception e){
            e.printStackTrace();
            log.error("online",e.getMessage());
        }
        return result;
    }

    //查询热门搜索列表
    @RequestMapping("list")
    public Result list(@RequestParam String keyWord,@RequestParam String operatorId){
        Result result = new Result();
        try {
            List<HotSearch> hotSearchList = hotSearchCmsService.queryHotSearchByQue(keyWord.trim(),operatorId);

            result = ResultUtil.success(hotSearchList);
        }catch (Exception e){
            e.printStackTrace();
            log.error("list",e.getMessage());
        }
        return result;
    }





}
