package com.memory.cms.service.impl;

import com.alibaba.fastjson.JSON;
import com.memory.cms.repository.UserHelpCmsRepository;
import com.memory.cms.service.UserHelpCmsService;
import com.memory.common.utils.FileUtils;
import com.memory.common.utils.Utils;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.UserHelp;
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
 * @ClassName UserHelpCmsServiceImpl
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/12 16:28
 */
@Service(value = "userHelpCmsService")
public class UserHelpCmsServiceImpl implements UserHelpCmsService {

    @Autowired
    private UserHelpCmsRepository userHelpCmsRepository;

    @Autowired
    private DaoUtils daoUtils;

    /**
     * 验证标题是否存在
     * @param helpTitle
     * @param id
     * @return
     */
    @Override
    public UserHelp checkHelpTitle(String helpTitle, String id){
        StringBuffer sb = new StringBuffer(" from UserHelp where helpTitle=:helpTitle ");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("helpTitle", helpTitle);
        if(!"".equals(id)){
            sb.append(" and id <>:id ");
            map.put("id", id);
        }
        UserHelp userHelp = (UserHelp) daoUtils.findObjectHQL(sb.toString(), map);
        return userHelp;
    }

    /**
     * 添加用户帮助
     * @param helpTitle
     * @param helpSubtitle
     * @param helpType
     * @param helpSort
     * @param createId
     */
    @Transactional
    @Override
    public void add(String helpTitle, String helpSubtitle, Integer helpType, Integer helpSort, String createId, MultipartFile helpLogo, MultipartFile helpContent){
        Date date = new Date();
        UserHelp userHelp = new UserHelp();
        String id = Utils.generateUUIDs();
        userHelp.setId(id);
        userHelp.setHelpTitle(helpTitle);
        userHelp.setHelpSubtitle(helpSubtitle);
        userHelp.setHelpType(helpType);
        userHelp.setHelpCreateTime(date);
        userHelp.setHelpCreateId(createId);
        userHelp.setHelpUpdateTime(date);
        userHelp.setHelpUpdateId(createId);
        userHelp.setHelpSort(helpSort);
        userHelp.setUseYn(1);

        String prefixContent = "helpContent";
        String prefixLogo = "helpLogo";

        if(Utils.isNotNull(helpLogo)){
            userHelp.setHelpLogo(getImgUrl(helpLogo, id, prefixLogo));
        }
        if(Utils.isNotNull(helpContent)){
            userHelp.setHelpContent(getImgUrl(helpContent, id, prefixContent));

        }

        userHelpCmsRepository.save(userHelp);

    }

    private String getImgUrl(MultipartFile helpLogo, String id, String prefix) {
        String fileName = FileUtils.getImgFileName(prefix);
        String customCmsPath = FileUtils.getCustomCmsPath("userHelp", id);
        String imgUrl1 = FileUtils.upload(helpLogo, FileUtils.getLocalPath(), customCmsPath, fileName);
        return imgUrl1;
    }

    /**
     * 查询用户帮助
     * @param pageable
     * @param helpTitle
     * @param useYn
     * @return
     */
    @Override
    public Page<UserHelp> findUserHelp(Pageable pageable, String helpTitle, Integer useYn){
        Specification specification =new Specification<UserHelp>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();

                if(!"".equals(helpTitle)){
                    list.add(criteriaBuilder.like(root.get("helpTitle"),"%" + helpTitle + "%"));
                }
                if(useYn!=null){
                    list.add(criteriaBuilder.equal(root.get("useYn"), useYn ));
                }

                Predicate[] p = new Predicate[list.size()];
                criteriaQuery.where(criteriaBuilder.and(list.toArray(p)));
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get("helpUpdateTime")));

                return criteriaQuery.getRestriction();
            }
        };
        return userHelpCmsRepository.findAll(specification,pageable);
    }

    /**
     * 修改用户帮助
     * @param userHelp
     * @param helpTitle
     * @param helpSubtitle
     * @param helpType
     * @param helpSort
     * @param createId
     */
    @Transactional
    @Override
    public UserHelp upd(UserHelp userHelp,String helpTitle, String helpSubtitle, Integer helpType, Integer helpSort,String createId,MultipartFile helpLogo,MultipartFile helpContent){
        Date date = new Date();
        String id = Utils.generateUUIDs();
        userHelp.setHelpTitle(helpTitle);
        userHelp.setHelpSubtitle(helpSubtitle);
        userHelp.setHelpType(helpType);
        userHelp.setHelpUpdateTime(date);
        userHelp.setHelpUpdateId(createId);
        userHelp.setHelpSort(helpSort);
        String prefixLogo = "helpLogo";
        String prefixContent = "helpContent";
        if(Utils.isNotNull(helpLogo)){
            userHelp.setHelpLogo( getImgUrl(helpLogo, id, prefixLogo));
        }
        if(Utils.isNotNull(helpContent)){
            userHelp.setHelpContent(getImgUrl(helpContent, id, prefixContent));

        }


        userHelpCmsRepository.save(userHelp);
        return userHelp;

    }

    /**
     * 设置是否显示
     * @param userHelp
     * @return
     */
    @Override
    @Transactional
    public UserHelp upduseYn (UserHelp userHelp,String operatorId){
        if (userHelp.getUseYn()==1){
            userHelp.setUseYn(0);
        }else {
            userHelp.setUseYn(1);
        }
        userHelp.setHelpUpdateTime(new Date());
        userHelp.setHelpUpdateId(operatorId);
        return userHelpCmsRepository.save(userHelp);
    }

    /**
     * 删除
     * @param userHelp
     */
    @Override
    @Transactional
    public void del(UserHelp userHelp){
        userHelpCmsRepository.delete(userHelp);
    }

    @Override
    public UserHelp getUserHelpById(String id) {
        if(userHelpCmsRepository.findById(id).hashCode() != 0){
            return userHelpCmsRepository.findById(id).get();
        }else{
            return null;
        }
    }

    @Override
    public UserHelp queryUserHelpByHelpTitle(String helpTitle) {
        return userHelpCmsRepository.queryUserHelpByHelpTitle(helpTitle);
    }
}
