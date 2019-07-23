package com.memory.gwzz.service.impl;

import com.memory.common.utils.Utils;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.Course;
import com.memory.entity.jpa.CourseComment;
import com.memory.entity.jpa.CourseCommentLike;
import com.memory.entity.jpa.User;
import com.memory.gwzz.redis.service.CourseCommentRedisMobileService;
import com.memory.gwzz.redis.service.CourseRedisMobileService;
import com.memory.gwzz.repository.CourseCommentLikeMobileRepository;
import com.memory.gwzz.repository.CourseCommentMobileRepository;
import com.memory.gwzz.service.CourseCommentMobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @ClassName CourseCommentMobileServiceImpl
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/4 16:43
 */
@Service("courseCommentMobileService")
public class CourseCommentMobileServiceImpl implements CourseCommentMobileService {
    @Autowired
    private CourseCommentRedisMobileService courseCommentRedisMobileService;

    @Autowired
    private CourseCommentLikeMobileRepository courseCommentLikeMobileRepository;

    @Autowired
    private DaoUtils daoUtils;

    @Transactional
    @Override
    public synchronized Map<String, Object> add(String courseId, User user, Integer commentType,String commentParentId, String content, String content_replace) {
        Map<String,Object> returnMap = new HashMap<>();
        Date date = new Date();
        CourseComment courseComment = new CourseComment();
        Course course = (Course) daoUtils.getById("Course",courseId);
        if (course!=null){
            String userId = user.getId();
            String courseCommentId= Utils.generateUUIDs();
            courseComment.setId(courseCommentId);
            courseComment.setCourseId(courseId);
            courseComment.setUserId(userId);
            courseComment.setUserLogo(user.getUserLogo());
            courseComment.setUserName(user.getUserNickName());
            courseComment.setCommentType(commentType);
            if (commentType==0){
                courseComment.setCommentRootId(courseCommentId);
                courseComment.setCommentParentId("");
                courseComment.setCommentParentUserName("");
                courseComment.setCommentParentContent("");
            }else if (commentType==1){
                CourseComment cc = this.getByPid(commentParentId);
                courseComment.setCommentRootId(cc.getCommentRootId());
                courseComment.setCommentParentId(commentParentId);
                courseComment.setCommentParentUserName("@"+cc.getUserName());
                courseComment.setCommentParentContent(cc.getCommentContentReplace());
            }
            courseComment.setCommentContent(content);
            courseComment.setCommentContentReplace(content_replace);
            courseComment.setCommentCreateTime(date);
            courseComment.setCommentTotalLike(0);

//            course.setCourseTotalComment(course.getCourseTotalComment()+1);
            course.setCourseTotalComment(this.getCommentCount(courseId));
        }

        daoUtils.save(course);
        daoUtils.save(courseComment);
        returnMap.put("courseComment",courseComment);

        return returnMap;
    }

    public CourseComment getByPid(String pid){
        return (CourseComment) daoUtils.getById("CourseComment",pid);
    }

    /**
     * 查询课程一级评论
     * @param courseId
     * @param start
     * @param limit
     * @return
     */
    @Override
    public Map<String, Object> listComByCid(String courseId,Integer start,Integer limit,String uid) {
        Map<String,Object> returnMap = new HashMap<>();
        Course course = (Course) daoUtils.getById("Course",courseId);

        //查询一级评论列表
        StringBuffer sbCourseCommentList = new StringBuffer("select id AS courseCommentId, course_id, user_id AS uid, user_logo, user_name, comment_content_replace, comment_create_time, comment_total_like," +
                "(select count(*) from course_comment where course_id=:courseId and comment_root_id = courseCommentId and comment_type=1) " +
//                "(SELECT ccl.comment_like_yn FROM course_comment_like ccl WHERE ccl.comment_id = courseCommentId AND ccl.user_id ='"+uid+"') " +
                "from course_comment where course_id=:courseId AND comment_type=0 order by comment_create_time desc");
        //查询一级评论总数
        StringBuffer sbCount = new StringBuffer("select count(*) from course_comment where course_id=:courseId AND comment_type=0 ");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("courseId", courseId);
        DaoUtils.Page page = new DaoUtils.Page();
        page.setPageIndex(start);
        page.setLimit(limit);
        List<Object[]> list = daoUtils.findBySQL(sbCourseCommentList.toString(),map,page,null);
        List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> objMap=new HashMap<String, Object>();
            String courseCommentId = list.get(i)[0].toString();
            Integer isLike = courseCommentRedisMobileService.isLike(courseCommentId,uid);
            Integer commentTotalLike = courseCommentRedisMobileService.getCourseCommentLike(courseId,courseCommentId);
            objMap.put("id", courseCommentId);
            objMap.put("courseId", list.get(i)[1]);
            objMap.put("userId", list.get(i)[2]);
            objMap.put("userLogo", list.get(i)[3]);
            objMap.put("userName", list.get(i)[4]);
            objMap.put("commentContentReplace", list.get(i)[5]);
            objMap.put("commentCreateTime", list.get(i)[6]);

            objMap.put("commentTotalLike", commentTotalLike);
            objMap.put("commentReplySum", list.get(i)[8]);


            objMap.put("commentLike", isLike);

            returnList.add(objMap);
        }

        Integer commentCount = daoUtils.getTotalBySQL(sbCount.toString(),map);

        returnMap.put("listCouCom",returnList);
        returnMap.put("commentCount",commentCount);
        returnMap.put("count",course.getCourseTotalComment());


        return returnMap;
    }

    @Override
    public Map<String, Object> listCouComByRid(String commentId,String uid, Integer start, Integer limit) {
        Map<String,Object> returnMap = new HashMap<>();
        //查询一级评论对象
        CourseComment courseComment = (CourseComment) daoUtils.getById("CourseComment",commentId);
        if (courseComment!=null){
//            CourseCommentLike courseCommentLike =courseCommentLikeMobileRepository.findByCommentIdAndUserId(commentId,uid);
            Integer isCommentLike = courseCommentRedisMobileService.isLike(commentId,uid);
            Integer conmmentLikeSum = courseCommentRedisMobileService.getCourseCommentLike(courseComment.getCourseId(),commentId);
            courseComment.setCommentTotalLike(conmmentLikeSum);

            String commentRootId = courseComment.getCommentRootId();
            //查询子级评论列表
            StringBuffer sbCCTWO = new StringBuffer("select id,course_id,user_id,user_logo,user_name,comment_content_replace,comment_parent_user_name,comment_parent_content," +
                    "comment_create_time,comment_total_like from course_comment where comment_root_id = '"+commentRootId+"' and comment_type=1 " +
                    " order by comment_create_time desc");
            StringBuffer sbCount = new StringBuffer("select count(*) from course_comment where  comment_root_id = '"+commentRootId+"' and comment_type=1 ");
            DaoUtils.Page page = new DaoUtils.Page();
            page.setPageIndex(start);
            page.setLimit(limit);
            List<Object[]> list = daoUtils.findBySQL(sbCCTWO.toString(),null,page,null);
            List<Map<String, Object>> twoList=new ArrayList<Map<String,Object>>();
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> objMap=new HashMap<String, Object>();
                objMap.put("id", list.get(i)[0]);
                objMap.put("courseId", list.get(i)[1]);
                objMap.put("userId", list.get(i)[2]);
                objMap.put("userLogo", list.get(i)[3]);
                objMap.put("userName", list.get(i)[4]);
                objMap.put("commentContentReplace", list.get(i)[5]);
                objMap.put("commentParentUserName", list.get(i)[6]);
                objMap.put("commentParentContent", list.get(i)[7]);
                objMap.put("commentCreateTime", list.get(i)[8]);
                objMap.put("commentTotalLike", list.get(i)[9]);

                twoList.add(objMap);
            }

            Integer commentCount = daoUtils.getTotalBySQL(sbCount.toString(),null);

            returnMap.put("courseComment",courseComment);
            returnMap.put("twoList",twoList);
            returnMap.put("commentCount",commentCount);
            returnMap.put("isCommentLike",isCommentLike);
        }else{
            returnMap.put("null",null);
        }
        return returnMap;
    }

    @Transactional
    @Override
    public void delCourseComment(String courseCommentId){
        try {
            CourseComment courseComment = (CourseComment) daoUtils.getById("CourseComment",courseCommentId);
            Integer type = courseComment.getCommentType();
            String  courseId = courseComment.getCourseId();
            Course course = (Course) daoUtils.getById("Course",courseId);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("courseCommentId", courseCommentId);
            if (type==0){//删除所有 rootId = articleCommentId
                StringBuffer sb1= new StringBuffer(" delete from course_comment  where comment_root_id=:courseCommentId ");
                daoUtils.excuteSQL(sb1.toString(),map);
            }else{//删除当前 以及 comment_parent_id = articleCommentId
                StringBuffer sb2 = new StringBuffer(" delete from course_comment  where id=:courseCommentId ");
                StringBuffer sb3 = new StringBuffer(" delete from course_comment  where comment_parent_id=:courseCommentId ");
                daoUtils.excuteSQL(sb2.toString(),map);
                daoUtils.excuteSQL(sb3.toString(),map);
            }
            course.setCourseTotalComment(this.getCommentCount(courseId));
            daoUtils.save(course);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //查询评论条数
    public int getCommentCount(String courseId){
        Integer count = 0;
        StringBuffer stringBuffer = new StringBuffer(" select count(*) from course_comment where course_id = '"+courseId+"' ");
        count = daoUtils.getTotalBySQL(stringBuffer.toString(),null);
        return count;
    }
}
