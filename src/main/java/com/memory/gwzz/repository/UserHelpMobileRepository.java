package com.memory.gwzz.repository;

import com.memory.entity.jpa.UserHelp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @ClassName UserHelpMobileRepository
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/10 15:34
 */
public interface UserHelpMobileRepository extends JpaRepository<UserHelp,String> {
    List<UserHelp> findByHelpTypeAndUseYnOrderByHelpSortAsc(Integer helpType, Integer useYn);

}
