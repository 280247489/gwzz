package com.memory.cms.repository;

import com.memory.entity.jpa.UserHelp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName UserHelpCmsRepository
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/12 16:26
 */
public interface UserHelpCmsRepository extends JpaRepository<UserHelp,String>, JpaSpecificationExecutor {

}
