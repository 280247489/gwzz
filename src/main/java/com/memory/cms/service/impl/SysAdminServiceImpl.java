package com.memory.cms.service.impl;

import com.memory.cms.entity.SysAdmin;
import com.memory.cms.repository.SysAdminRepository;
import com.memory.cms.service.SysAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


/**
 * @author INS6+
 * @date 2019/5/7 15:57
 */
@Service
public class SysAdminServiceImpl implements SysAdminService {

    @Autowired
    private SysAdminRepository repository;


    @Override
    public List<SysAdmin> getSysAdminList() {
        return  repository.findAll();
    }

    @Override
    public SysAdmin getSysAdminById(String id) {
        if(repository.findById(id).hashCode() != 0){
            return repository.findById(id).get();
        }else{
            return null;
        }
    }

    @Override
    public SysAdmin add(SysAdmin sysAdmin) {
        return repository.save(sysAdmin);
    }

    @Override
    public SysAdmin update(SysAdmin sysAdmin) {
        return repository.save(sysAdmin);
    }

    @Override
    public void delete(String id) {
         repository.deleteById(id);
    }

    @Override
    public SysAdmin querySysAdminByLoginName(String loginName) {
        return repository.querySysAdminByLoginname(loginName);
    }
}
