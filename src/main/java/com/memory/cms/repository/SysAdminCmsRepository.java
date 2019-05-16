package com.memory.cms.repository;

import com.memory.entity.jpa.SysAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author INS6+
 * @date 2019/5/7 15:55
 */

public interface SysAdminCmsRepository extends JpaRepository<SysAdmin,String> {


    SysAdmin querySysAdminByLoginname(String loginName);




}
