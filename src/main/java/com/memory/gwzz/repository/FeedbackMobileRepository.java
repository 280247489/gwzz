package com.memory.gwzz.repository;

import com.memory.entity.jpa.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName FeedbackMobileRepository
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/28 11:07
 */
public interface FeedbackMobileRepository extends JpaRepository<Feedback,String> {
}
