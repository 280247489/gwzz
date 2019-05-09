package com.memory.cms.repository;

import com.memory.cms.entity.SysAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author INS6+
 * @date 2019/5/7 15:55
 */

public interface SysAdminRepository extends JpaRepository<SysAdmin,String> {


    SysAdmin querySysAdminByLoginname(String loginName);




}
