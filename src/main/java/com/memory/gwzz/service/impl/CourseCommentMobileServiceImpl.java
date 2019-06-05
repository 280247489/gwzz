package com.memory.gwzz.service.impl;

import com.memory.common.utils.Utils;
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

    @Override
    public Map<String, Object> add(String courseId, String userId, String userLogo, String userName, Integer commentType,
                                   String commentParentId, String commentParentUserName, String commentContent) {
        Map<String,Object> returnMap = new HashMap<>();
        String cpId = null;
        String rid = null;
        Date date = new Date();
        if(commentType==0){
            cpId = courseId;
            rid = courseId;
        }else if (commentType==1){
            cpId = commentParentId;
            rid = courseId;
        }
        CourseComment courseComment = new CourseComment(Utils.generateUUIDs(),courseId,userId,userLogo,userName,commentType,
                rid,cpId,commentParentUserName,commentContent,date,0);
        courseCommentMobileRepository.save(courseComment);
        returnMap.put("courseComment",commentContent);

        return returnMap;
    }
}
