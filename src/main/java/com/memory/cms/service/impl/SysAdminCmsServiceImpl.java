package com.memory.cms.service.impl;

import com.memory.entity.jpa.SysAdmin;
import com.memory.cms.repository.SysAdminCmsRepository;
import com.memory.cms.service.SysAdminCmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


/**
 * @author INS6+
 * @date 2019/5/7 15:57
 */
@Service
public class SysAdminCmsServiceImpl implements SysAdminCmsService {

    @Autowired
    private SysAdminCmsRepository repository;


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
