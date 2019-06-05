package com.memory.gwzz.repository;

import com.memory.entity.jpa.CourseCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName CourseCommentLikeMobileRepository
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/4 16:40
 */
public interface CourseCommentLikeMobileRepository extends JpaRepository<CourseCommentLike,String> {
    CourseCommentLike findByCommentIdAndUserId(String cid,String uid);
}
