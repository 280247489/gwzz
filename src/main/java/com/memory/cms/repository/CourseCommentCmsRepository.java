package com.memory.cms.repository;
import com.memory.entity.jpa.CourseComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/30 10:26
 */

public interface CourseCommentCmsRepository extends JpaRepository<CourseComment,String>, JpaSpecificationExecutor {


    @Query(value = "select  new com.memory.entity.jpa.CourseComment(c.id,c.courseId,c.userId,c.userLogo,c.userName,c.commentType,c.commentRootId,c.commentParentId,c.commentParentUserName,c.commentContent,c.commentCreateTime,c.commentTotalLike ) " +
            "from CourseComment c  where  c.commentRootId =?1  AND c.commentCreateTime >?2 ORDER BY  c.commentCreateTime desc")
    List<CourseComment> queryCourseCommentList(String  comment_root_id, Date comment_create_time );

    CourseComment getCourseCommentById(String id);

    @Modifying
    @Query(value = "delete from CourseComment  where commentRootId =?1 ")
    void  deleteCourseCommentByCommentRootIds(String root_id);





}
