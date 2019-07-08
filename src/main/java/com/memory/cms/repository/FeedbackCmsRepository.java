package com.memory.cms.repository;

import com.memory.entity.jpa.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author INS6+
 * @date 2019/7/3 13:55
 */

public interface FeedbackCmsRepository extends JpaRepository<Feedback,String> {
}
