package com.memory.cms.repository;

import com.memory.entity.jpa.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName BannerCmsRepository
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/11 18:09
 */
public interface BannerCmsRepository extends JpaRepository<Banner,String>, JpaSpecificationExecutor {
}
