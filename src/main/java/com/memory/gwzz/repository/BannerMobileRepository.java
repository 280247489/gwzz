package com.memory.gwzz.repository;

import com.memory.entity.jpa.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName BannerMobileRepository
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/22 17:07
 */
public interface BannerMobileRepository extends JpaRepository<Banner,String> {
    Banner findByIdAndBannerOnline(String id,Integer onLine);
}
