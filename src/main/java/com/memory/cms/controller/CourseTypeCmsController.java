package com.memory.cms.controller;
import com.memory.cms.service.CourseTypeCmsService;
import com.memory.common.utils.*;
import com.memory.entity.jpa.CourseTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping(value = "courseType")
public class CourseTypeCmsController {

    private static final String fileUrl = "G:/upload";

    @Autowired
    private CourseTypeCmsService courseTypeService;

    @RequestMapping(value = "options"/*, method = RequestMethod.POST*/)
    public Result getCourseTypes(){
        Result result = new Result();
        try {
            Sort sort = new Sort(Sort.Direction.DESC,"type_create_time");
            List<CourseTypes> list = courseTypeService.queryCourseTypeList(sort);
            result = ResultUtil.success(list);

        }catch (Exception e){
            e.printStackTrace();
            result = ResultUtil.error(-1,"系统异常");
        }

        return result;
    }

    @RequestMapping(value = "list"/*, method = RequestMethod.POST*/)
    public Result queryCourseTypeList(){
        Result result = new Result();
        try{
            Sort sort = new Sort(Sort.Direction.DESC,"type_create_time");
            List<CourseTypes> list = courseTypeService.queryCourseTypeList(sort);
            result = ResultUtil.success(list);

        }catch (Exception e){
            e.printStackTrace();
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
    @RequestMapping(value = "add"/*, method = RequestMethod.POST*/)
    public  Result add(@RequestParam(value = "imgUrl" ,required = false) String imgUrl, Integer isUse, String typeName, @RequestParam(value = "typeFile" ,required = false) MultipartFile typeFile){
        Result result = new Result();
        try{

            String prefix = "";
            String suffix = "";
            String dayStr = DateUtils.getDate("yyyyMMdd");
            String hoursStr = DateUtils.getDate("HHmmss");
            String fileUploadedPath = "";
            String fileName="";
            String uuid = Utils.generateUUID();


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

            CourseTypes courseType = new CourseTypes();
            courseType.setId(uuid);
            courseType.setImg(imgUrl);
            courseType.setIsUse(isUse);
            courseType.setSum(0);
            courseType.setTypeCreateTime(new Date());
            courseType.setTypeName(typeName);
            CourseTypes courseTypeSave = courseTypeService.add(courseType);
            result = ResultUtil.success(courseTypeSave);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }






}
