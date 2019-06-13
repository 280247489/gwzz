package com.memory.gwzz.repository;

import com.memory.entity.jpa.ShareUrl;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName ShareUrlMobileRepository
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/12 20:43
 */
public interface ShareUrlMobileRepository extends JpaRepository<ShareUrl,String> {
    ShareUrl findByUrlName(String urlName);
}
