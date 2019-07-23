package com.memory.gwzz.repository;

import com.memory.entity.jpa.HotSearch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @ClassName HotSearchMobileRepository
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/7/23 10:43
 */
public interface HotSearchMobileRepository extends JpaRepository<HotSearch,String > {
    List<HotSearch> findByStatusOrderBySortDesc(Integer status);
}
