package com.memory.gwzz.service.impl;

import com.memory.common.utils.Utils;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.CourseComment;
import com.memory.gwzz.repository.CourseCommentMobileRepository;
import com.memory.gwzz.service.CourseCommentMobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName CourseCommentMobileServiceImpl
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/4 16:43
 */
@Service("courseCommentMobileService")
public class CourseCommentMobileServiceImpl implements CourseCommentMobileService {
    @Autowired
    private CourseCommentMobileRepository courseCommentMobileRepository;

    @Autowired
    private DaoUtils daoUtils;

    @Override
    public Map<String, Object> add(String courseId, String userId, String userLogo, String userName, Integer commentType,
                                   String commentParentId, String commentParentUserName, String commentContent) {
        Map<String,Object> returnMap = new HashMap<>();
        String crId = "";
        String cpId = "";
        String cpUserName = "";
        Date date = new Date();
        if (commentType==0){
            crId = userId;
            cpId = "";
            cpUserName = "";
        }else{
            CourseComment cc = this.getByPid(commentParentId);
            crId = cc.getCommentRootId();
            cpId = commentParentId;
            cpUserName =
                    "回复@"+commentParentUserName+cc.getCommentContent();
        }
//        CourseComment courseComment = new CourseComment(Utils.generateUUIDs(),courseId,userId,userLogo,userName,commentType,
//                crId,cpId,cpUserName,commentContent,date,0);
//        courseCommentMobileRepository.save(courseComment);
//        returnMap.put("courseComment",courseComment);

        return returnMap;
    }

    public CourseComment getByPid(String pid){
        return (CourseComment) daoUtils.getById("CourseComment",pid);
    }
}
