package com.memory.gwzz.repository;

import com.memory.entity.jpa.CourseLike;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName CourseLikeMobileRepository
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/4 16:37
 */
public interface CourseLikeMobileRepository extends JpaRepository<CourseLike,String> {
    CourseLike findByCourseIdAndUserId(String cid,String uid);
}
