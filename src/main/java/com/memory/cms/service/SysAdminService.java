package com.memory.cms.service;

import com.memory.cms.entity.SysAdmin;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/7 15:56
 */

public interface SysAdminService {

    List<SysAdmin> getSysAdminList();

    SysAdmin getSysAdminById(String id);

    SysAdmin add(SysAdmin sysAdmin);

    SysAdmin update(SysAdmin sysAdmin);

    void delete(String id);

    SysAdmin querySysAdminByLoginName(String loginName);


}
