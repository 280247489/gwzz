package com.memory.cms.controller;

import com.memory.cms.service.ArticleTypeCmsService;
import com.memory.common.utils.*;
import com.memory.common.yml.MyFileConfig;
import com.memory.entity.jpa.ArticleType;
import com.memory.entity.jpa.CourseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/9 16:31
 */
@RestController
@RequestMapping(value = "articleType/cms")
public class ArticleTypeCmsController {

    private static final Logger log = LoggerFactory.getLogger(ArticleTypeCmsController.class);


    @Autowired
    private ArticleTypeCmsService articleTypeService;

    @Autowired
    private MyFileConfig myFileConfig;


    @RequestMapping(value = "options"/*, method = RequestMethod.POST*/)
    public Result getArticleTypes(){
        Result result = new Result();
        try {
            List<ArticleType> list = articleTypeService.queryArticleTypeList(1);
            result = ResultUtil.success(list);

        }catch (Exception e){
            e.printStackTrace();
            result = ResultUtil.error(-1,"系统异常");
        }

        return result;
    }



    @RequestMapping(value = "list", method = RequestMethod.POST)
    public Result queryArticleTypeList(){
        Result result = new Result();
        try{
            List< ArticleType> list = articleTypeService.queryArticleTypeList();
            result = ResultUtil.success(list);

        }catch (Exception e){
            e.printStackTrace();
            log.error("articleType/cms/list  err =",e.getMessage());
        }
        return result;
    }

    /**
     * 添加分类
     * @param imgUrl
     * @param isUse
     * @param typeName
     * @param typeFile
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public  Result add(@RequestParam(value = "imgUrl" ,required = false) String imgUrl, Integer isUse, String typeName, @RequestParam(value = "typeFile" ,required = false) MultipartFile typeFile){
        Result result = new Result();
        try{

            String prefix = "";
            String suffix = "";
            String dayStr = DateUtils.getDate("yyyyMMdd");
            String hoursStr = DateUtils.getDate("HHmmss");
            String fileUploadedPath = "";
            String fileName="";
            String uuid = Utils.getShortUUTimeStamp();
            String fileUrl = myFileConfig.getUpload_local_article_path();
            if(typeFile != null && !typeFile.isEmpty()){
                prefix = "type";
                //图片默认转成png格式
                suffix = ".png";
                fileName = prefix + "_" + dayStr + "_" + hoursStr + suffix;

                fileUploadedPath = fileUrl + "/" + uuid;
                //上传标题图
                FileUtils.upload(typeFile,fileUploadedPath,fileName);
                imgUrl = fileUploadedPath + "/" +fileName;

            }

            ArticleType articleType = new ArticleType();
            articleType.setId(uuid);
            articleType.setImg(imgUrl);
            articleType.setIsUse(isUse);
            articleType.setSum(0);
            articleType.setTypeCreateTime(new Date());
            articleType.setTypeName(typeName);
            ArticleType articleTypeSave = articleTypeService.add(articleType);
            result = ResultUtil.success(articleTypeSave);
        }catch (Exception e){
            e.printStackTrace();
            log.error("articleType/cms/add  err =",e.getMessage());
        }
        return result;
    }





}
