package com.memory.cms.service.impl;

import com.memory.cms.repository.BannerCmsRepository;
import com.memory.cms.service.BannerCmsService;
import com.memory.common.utils.FileUploadUtil;
import com.memory.common.utils.Utils;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.Banner;
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
import java.util.*;

/**
 * @ClassName BannerCmsServiceImpl
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/11 18:12
 */
@Service(value = "bannerCmsService")
public class BannerCmsServiceImpl implements BannerCmsService {
    @Autowired
    private BannerCmsRepository bannerCmsRepository;

    @Autowired
    private DaoUtils daoUtils;
    String filePath ="D:\\Tomcat 7.0\\webapps";
    String dbUrl = "/gwzz_file/Advertise/logo/";

    /**
     * 添加
     * @param bName
     * @param bLogo
     * @param typeTable
     * @param typeTableId
     * @param bannerSort
     * @param createId
     */
    @Transactional
    @Override
    public void addBanner(String bName, MultipartFile bLogo, String typeTable, String typeTableId, Integer bannerSort, String createId){

        Date date = new Date();
        Banner banner = new Banner();
        banner.setId(Utils.generateUUIDs());
        banner.setBannerName(bName);
        if (bLogo!=null){
            banner.setBannerLogo(FileUploadUtil.uploadFile(bLogo,filePath,dbUrl,Utils.getShortUUTimeStamp()));
        }
        banner.setTypeTable(typeTable);
        banner.setTypeTableId(typeTableId);
        banner.setBannerSort(bannerSort);
        banner.setBannerOnline(0);
        banner.setBannerCreateTime(date);
        banner.setBannerCreateId(createId);
        banner.setBannerUpdateTime(date);
        banner.setBannerUpdateId(createId);
        bannerCmsRepository.save(banner);


    }

    /**
     * 根据名称验证信息
     * @param bName
     * @param id
     * @return
     */
    @Override
    public Banner checkBannerName(String bName, String id){
        StringBuffer sb = new StringBuffer(" from Banner where bannerName=:bannerName ");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("bannerName", bName);
        if(!"".equals(id)){
            sb.append(" and id!=:id ");
            map.put("id", id);
        }
        Banner banner = (Banner) daoUtils.findObjectHQL(sb.toString(), map);
        return banner;
    }

    /**
     * 分页查询
     * @param pageable
     * @param bName
     * @return
     */
    @Override
    public Page<Banner> findBanner(Pageable pageable, String bName){
        Specification specification =new Specification<Banner>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();

                if(!"".equals(bName)){
                    list.add(criteriaBuilder.like(root.get("bannerName"),"%" + bName + "%"));
                }
                Predicate[] p = new Predicate[list.size()];
                criteriaQuery.where(criteriaBuilder.and(list.toArray(p)));
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get("bannerSort")));
                return criteriaQuery.getRestriction();
            }
        };
        return bannerCmsRepository.findAll(specification,pageable);
    }

    /**
     * 修改
     * @param banner
     * @param bName
     * @param bLogo
     * @param typeTable
     * @param typeTableId
     * @param bannerSort
     * @param createId
     * @return
     */
    @Override
    @Transactional
    public Banner upd(Banner banner, String bName, MultipartFile bLogo, String typeTable, String typeTableId,Integer bannerSort, String createId) {
        Date date = new Date();
        banner.setBannerName(bName);
        if (bLogo!=null){
            banner.setBannerLogo(FileUploadUtil.uploadFile(bLogo,filePath,dbUrl,Utils.getShortUUTimeStamp()));
        }
        banner.setTypeTable(typeTable);
        banner.setTypeTableId(typeTableId);
        banner.setBannerSort(bannerSort);
        banner.setBannerUpdateId(createId);
        banner.setBannerUpdateTime(date);


        return bannerCmsRepository.save(banner);
    }

    /**
     * 设置上下线
     * @param banner
     * @return
     */
    @Override
    @Transactional
    public Banner updOnine (Banner banner){
        if (banner.getBannerOnline()==1){
            banner.setBannerOnline(0);
        }else {
            banner.setBannerOnline(1);
        }
        return bannerCmsRepository.save(banner);
    }

    /**
     * 删除
     * @param banner
     */
    @Override
    @Transactional
    public void del(Banner banner){
        daoUtils.del(banner);
    }


}
