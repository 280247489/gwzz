package com.memory.cms.repository;

import com.memory.cms.entity.UserInfos;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * @author INS6+
 * @date 2019/5/7 10:17
 */

public interface UserRepository extends JpaRepository<UserInfos,String> {




}
