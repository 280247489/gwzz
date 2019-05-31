package com.memory.cms.controller;

import com.memory.cms.service.CourseCommentCmsService;
import com.memory.common.utils.PageResult;
import com.memory.common.utils.Result;
import com.memory.common.utils.ResultUtil;
import com.memory.common.utils.Utils;
import com.memory.entity.bean.CourseComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/30 11:25
 */
@RestController
@RequestMapping(value = "courseComment/cms")
public class CourseCommentCmsController {

    @Autowired
    private CourseCommentCmsService courseCommentCmsService;


    @RequestMapping(value = "list")
    public Result queryArticleCommentByQue(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,
                                           @RequestParam("key_words") String key_words, @RequestParam("phone_number") String phone_number,
                                           @RequestParam("course_name") String course_name, @RequestParam("user_name") String user_name,
                                           @RequestParam("comment_type") Integer comment_type, @RequestParam("query_start_time") String query_start_time,
                                           @RequestParam("query_end_time") String query_end_time, @RequestParam("sort_role") Integer sort_role,
                                           @RequestParam("comment_root_id") String comment_root_id,@RequestParam("id") String id){

        int pageIndex = page+1;
        int limit = size;
        List<CourseComment> list = courseCommentCmsService.queryCourseCommentByQueHql( pageIndex, limit, key_words, phone_number,  course_name,  user_name,  comment_type,  query_start_time,  query_end_time,  sort_role,comment_root_id, id);
        int totalElements = courseCommentCmsService.queryCourseCommentByQueHqlCount(  key_words, phone_number,  course_name,  user_name,  comment_type,  query_start_time,  query_end_time,  sort_role,comment_root_id,id);
        int totalPages = totalElements/size;
        if(totalElements%size != 0){
            totalPages+=1;
        }
        PageResult pageResult = new PageResult();
        pageResult.setPageNumber(page + 1);
        pageResult.setOffset(0L);
        pageResult.setPageSize(size);
        pageResult.setTotalPages(totalPages);
        pageResult.setTotalElements(Long.valueOf(totalElements));
        pageResult.setData(list);
        return ResultUtil.success(pageResult);
    }


    /**
     *
     * @param user_id
     * @param user_logo
     * @param user_name
     * @param comment_parent_id
     * @param comment_content
     * @return
     */
    @RequestMapping(value = "add")
    public  Result addAdminComment(@RequestParam("user_id") String user_id,@RequestParam("user_logo") String user_logo,@RequestParam("user_name") String user_name,@RequestParam("comment_parent_id") String comment_parent_id,@RequestParam("comment_content") String comment_content  ){
        Result result = new Result();
        try{
            com.memory.entity.jpa.CourseComment parentCourseComment = courseCommentCmsService.queryCourseCommentById(comment_parent_id);
            com.memory.entity.jpa.CourseComment courseComment = new    com.memory.entity.jpa.CourseComment();
            courseComment.setId(Utils.getShortUUTimeStamp());
            courseComment.setUserId(user_id);
            courseComment.setUserLogo(user_logo);
            courseComment.setUserName(user_name);
            courseComment.setCourseId(parentCourseComment.getCourseId());
            courseComment.setCommentType(1);
            courseComment.setCommentRootId(parentCourseComment.getCommentRootId());
            courseComment.setCommentParentId(comment_parent_id);
            if(parentCourseComment.getCommentType() == 1){
                courseComment.setCommentParentUserName("回复@"+parentCourseComment.getUserName());
            }else{
                courseComment.setCommentParentUserName("");
            }

            courseComment.setCommentParentUserName(parentCourseComment.getCommentParentUserName());
            courseComment.setCommentContent(comment_content);
            courseComment.setCommentCreateTime(new Date());
            courseComment.setCommentTotalLike(0);
            com.memory.entity.jpa.CourseComment courseComment1 =   courseCommentCmsService.addCourseComment(courseComment );
            result = ResultUtil.success(courseComment1);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


    @RequestMapping(value = "remove")
    public  Result remove(@RequestParam("comment_id") String comment_id){
        Result result = new Result();
        try{


            List<com.memory.entity.jpa.CourseComment> removeList = new ArrayList<>();

            com.memory.entity.jpa.CourseComment courseComment = courseCommentCmsService.queryCourseCommentById(comment_id);

            if(courseComment == null){
                return ResultUtil.error(-1,"非法id");
            }
            String comment_root_id = courseComment.getCommentRootId();


            if(comment_id.equals(comment_root_id)){
                courseCommentCmsService.deleteCourseCommentByCommentRootId(comment_root_id);
            }else{
                Date comment_create_time = courseComment.getCommentCreateTime();
                List< com.memory.entity.jpa.CourseComment> list = courseCommentCmsService.queryCourseCommentList(comment_root_id,comment_create_time);

                if(list.size()<=100){
                    removeComment(list, comment_id,removeList);
                }else{
                    removeList.add(courseComment);
                    result = ResultUtil.error(-1,"递归删除长度大于100，只删除当前评论!");
                }

            }
            courseCommentCmsService.deleteAll(removeList);
            result = ResultUtil.success("删除成功");

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }



    public List<com.memory.entity.jpa.CourseComment> removeComment(List< com.memory.entity.jpa.CourseComment> list, String parentId,  List<com.memory.entity.jpa.CourseComment> removeList){

        if(list.size()<=100){
            for (int i = 0; i < list.size(); i++) {
                com.memory.entity.jpa.CourseComment dg_obj = list.get(i);
                if(parentId.equals(dg_obj.getCommentParentId())){
                    removeList.add(dg_obj);
                    removeComment(list, dg_obj.getId(),removeList);
                }
            }
        }else{
            System.out.println("递归数据大于100");
        }
        return removeList;
    }



}
