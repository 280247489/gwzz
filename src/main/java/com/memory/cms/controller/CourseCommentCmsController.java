package com.memory.cms.controller;

import com.alibaba.fastjson.JSON;
import com.memory.cms.service.CourseCmsService;
import com.memory.cms.service.CourseCommentCmsService;
import com.memory.cms.service.SysAdminCmsService;
import com.memory.common.utils.PageResult;
import com.memory.common.utils.Result;
import com.memory.common.utils.ResultUtil;
import com.memory.common.utils.Utils;
import com.memory.entity.bean.CourseComment;
import com.memory.entity.jpa.Course;
import com.memory.entity.jpa.SysAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @Autowired
    private SysAdminCmsService sysAdminCmsService;

    @Autowired
    private CourseCmsService courseCmsService;

    @RequestMapping(value = "list")
    public Result queryArticleCommentByQue(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,
                                           @RequestParam("key_words") String key_words, @RequestParam("phone_number") String phone_number,
                                           @RequestParam("course_name") String course_name, @RequestParam("user_name") String user_name,
                                           @RequestParam("comment_type") Integer comment_type, @RequestParam("query_start_time") String query_start_time,
                                           @RequestParam("query_end_time") String query_end_time, @RequestParam("sort_role") Integer sort_role,
                                           @RequestParam("comment_root_id") String comment_root_id,@RequestParam("id") String id,@RequestParam(value ="course_id",required = false) String course_id){

        int pageIndex = page+1;
        int limit = size;
        List<CourseComment> list = courseCommentCmsService.queryCourseCommentByQueHql( pageIndex, limit, key_words, phone_number,  course_name,  user_name,  comment_type,  query_start_time,  query_end_time,  sort_role,comment_root_id, id,course_id);
        int totalElements = courseCommentCmsService.queryCourseCommentByQueHqlCount(  key_words, phone_number,  course_name,  user_name,  comment_type,  query_start_time,  query_end_time,  sort_role,comment_root_id,id,course_id);
        PageResult pageResult = PageResult.getPageResult(page, size, list, totalElements);
        return ResultUtil.success(pageResult);
    }


    /**
     *
     * @param user_id
     * @param comment_parent_id
     * @return
     */
    @RequestMapping(value = "add")
    public  Result addAdminComment(@RequestParam("user_id") String user_id,@RequestParam("comment_parent_id") String comment_parent_id,@RequestParam("content") String content ,@RequestParam("content_replace") String content_replace){
        Result result = new Result();
        try{

            SysAdmin sysAdmin =sysAdminCmsService.getSysAdminById(user_id);
            if(sysAdmin==null){
                return ResultUtil.error(-1,"非法用户！");
            }
            com.memory.entity.jpa.CourseComment parentCourseComment = courseCommentCmsService.queryCourseCommentById(comment_parent_id);
            com.memory.entity.jpa.CourseComment courseComment = new    com.memory.entity.jpa.CourseComment();
            courseComment.setId(Utils.getShortUUTimeStamp());
            courseComment.setUserId(user_id);
            courseComment.setUserLogo(sysAdmin.getLogo());
            courseComment.setUserName(sysAdmin.getId());
            courseComment.setCourseId(parentCourseComment.getCourseId());
            courseComment.setCommentType(1);
            courseComment.setCommentRootId(parentCourseComment.getCommentRootId());
            courseComment.setCommentParentId(comment_parent_id);
       //     if(parentCourseComment.getCommentType() == 1){
                System.out.println("parentUserName = " + "@"+parentCourseComment.getUserName());
                courseComment.setCommentParentUserName("@"+parentCourseComment.getUserName());
                courseComment.setCommentParentContent(parentCourseComment.getCommentContentReplace());
     /*       }else{
                courseComment.setCommentParentUserName("");
              //  courseComment.setCommentParentContent("");
            }*/
            courseComment.setCommentContent(content);
            courseComment.setCommentContentReplace(content_replace);
            courseComment.setCommentCreateTime(new Date());
            courseComment.setCommentTotalLike(0);
            com.memory.entity.jpa.CourseComment courseComment1 = courseCommentCmsService.addCourseComment(courseComment );


            Course course = courseCmsService.getCourseById(parentCourseComment.getCourseId());
            //查询当前课程的评论数量
            int courseTotalComment = courseCommentCmsService.getCourseTotalCommentByCourseId(parentCourseComment.getCourseId());
            System.out.println("now 评论数量 =="+courseTotalComment);
            //同步课程评论数
            course.setCourseTotalComment(courseTotalComment);
            courseCmsService.update(course);

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
            Course course = courseCmsService.getCourseById(courseComment.getCourseId());
            if(courseComment == null){
                return ResultUtil.error(-1,"非法id");
            }
            String comment_root_id = courseComment.getCommentRootId();


            if(courseComment.getCommentType() == 0){
                courseCommentCmsService.deleteCourseCommentByCommentRootId(comment_root_id);
            }else{
                removeList = courseCommentCmsService.queryCourseCommentByCommentParentId(courseComment.getId());
                removeList.add(courseComment);
                System.out.println("removeList ==============="+JSON.toJSONString(removeList));
                courseCommentCmsService.deleteAll(removeList);
            }

            //查询当前课程的评论数量
            int courseTotalComment = courseCommentCmsService.getCourseTotalCommentByCourseId(course.getId());
            //同步课程评论数
            course.setCourseTotalComment(courseTotalComment);
            courseCmsService.update(course);
            result = ResultUtil.success("删除成功");

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }



    public List<com.memory.entity.jpa.CourseComment> removeComment(List< com.memory.entity.jpa.CourseComment> list, String parentId,  List<com.memory.entity.jpa.CourseComment> removeList){

        if(list.size()<=100){
            if(list.size() == 1){
                removeList.add(list.get(0));
            }else{
                for (int i = 0; i < list.size(); i++) {
                    com.memory.entity.jpa.CourseComment dg_obj = list.get(i);
                    if(parentId.equals(dg_obj.getCommentParentId())){
                        removeList.add(dg_obj);
                        removeComment(list, dg_obj.getId(),removeList);
                    }
                }
            }

        }else{
            System.out.println("递归数据大于100");
        }
        return removeList;
    }



}