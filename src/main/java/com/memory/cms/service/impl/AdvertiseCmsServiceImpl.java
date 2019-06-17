package com.memory.cms.service.impl;

import com.memory.cms.repository.AdvertiseCmsRepository;
import com.memory.cms.service.AdvertiseCmsService;
import com.memory.common.utils.FileUploadUtil;
import com.memory.common.utils.FileUtils;
import com.memory.common.utils.Utils;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.Advertise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.swing.*;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * @ClassName AdvertiseCmsServiceImpl
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/11 11:15
 */
@Service(value = "advertiseCmsService")
public class AdvertiseCmsServiceImpl implements AdvertiseCmsService {

    @Autowired
    private AdvertiseCmsRepository advertiseCmsRepository;

    @Autowired
    private DaoUtils daoUtils;

    String filePath ="D:\\Tomcat 7.0\\webapps";

    String dbUrl = "/gwzz_file/Advertise/logo/";

    /**
     * 添加广告
     * @param aName
     * @param aLogo
     * @param aType
     * @param aH5Url
     * @param createId
     */
    @Transactional
    @Override
    public void addAdvertise(String aName, MultipartFile aLogo, Integer aType,String aH5Type, String aH5Url,String createId){

        Date date = new Date();
        String UUID = Utils.generateUUIDs();
        Advertise advertise = new Advertise();
        advertise.setId(UUID);
        advertise.setAdvertiseName(aName);
        if (!aLogo.isEmpty()){
            String fileName = FileUtils.getImgFileName("advertise");
            String customCmsPath = FileUtils.getCustomCmsPath("advertise",UUID);
            advertise.setAdvertiseLogo(FileUtils.upload(aLogo,FileUtils.getLocalPath(),customCmsPath,fileName));
        }
        advertise.setAdvertiseType(aType);
        advertise.setAdvertiseH5Type(aH5Type);
        advertise.setAdvertiseH5Url(aH5Url);
        advertise.setAdvertiseOnline(0);
        advertise.setAdvertiseCreateId(createId);
        advertise.setAdvertiseCreateTime(date);
        advertise.setAdvertiseUpdateId(createId);
        advertise.setAdvertiseUpdateTime(date);

        advertiseCmsRepository.save(advertise);


    }

    /**
     * 根据名称验证信息是否存在
     * @param aName
     * @return
     */
    @Override
    public Advertise checkAdvertiseName(String aName, String id){
        StringBuffer sb = new StringBuffer(" from Advertise where advertiseName=:advertiseName ");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("advertiseName", aName);
        if(!"".equals(id)){
            sb.append(" and id!=:id ");
            map.put("id", id);
        }
        Advertise advertise = (Advertise) daoUtils.findObjectHQL(sb.toString(), map);
        return advertise;
    }

    /**
     * 查询
     * @param pageable
     * @param aName
     * @param aType
     * @return
     */
    @Override
    public Page<Advertise> findAdvertise(Pageable pageable,String aName,Integer aType){
        Specification specification =new Specification<Advertise>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();

                if(!"".equals(aName)){
                    list.add(criteriaBuilder.like(root.get("advertiseName"),"%" + aName + "%"));
                }
                if(!"".equals(aType) && aType!=null){
                    list.add(criteriaBuilder.equal(root.get("advertiseType"), aType ));
                }

                Predicate[] p = new Predicate[list.size()];
                criteriaQuery.where(criteriaBuilder.and(list.toArray(p)));
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get("advertiseUpdateTime")));
                return criteriaQuery.getRestriction();
            }
        };
        return advertiseCmsRepository.findAll(specification,pageable);
    }

    /**
     * 修改
     * @param advertise
     * @param aName
     * @param aLogo
     * @param aH5Url
     * @param createId
     * @return
     */
    @Override
    @Transactional
    public Advertise upd(Advertise advertise, String aName, MultipartFile aLogo, String aH5Type, String aH5Url, String createId) {
        Date date = new Date();
        advertise.setAdvertiseName(aName);
        if (!aLogo.isEmpty()){
            String fileName = FileUtils.getImgFileName("advertise");
            String customCmsPath = FileUtils.getCustomCmsPath("advertise",advertise.getId());
            advertise.setAdvertiseLogo(FileUtils.upload(aLogo,FileUtils.getLocalPath(),customCmsPath,fileName));
        }
        advertise.setAdvertiseH5Type(aH5Type);
        advertise.setAdvertiseH5Url(aH5Url);
        advertise.setAdvertiseUpdateId(createId);
        advertise.setAdvertiseUpdateTime(date);

        return advertiseCmsRepository.save(advertise);
    }

    @Override
    @Transactional
    public Advertise updOnine (Advertise advertise){
        if (advertise.getAdvertiseOnline()==1){
            advertise.setAdvertiseOnline(0);
        }else {
            advertise.setAdvertiseOnline(1);
        }
        return advertiseCmsRepository.save(advertise);
    }

    @Override
    @Transactional
    public void del(Advertise advertise){
        advertiseCmsRepository.delete(advertise);
    }
}
