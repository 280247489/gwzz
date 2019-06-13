package com.memory.cms.service.impl;

import com.memory.cms.repository.ShareUrlCmsRepository;
import com.memory.cms.service.ShareUrlCmsService;
import com.memory.common.utils.Utils;
import com.memory.entity.jpa.ShareUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @ClassName ShareUrlCmsServiceImpl
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/12 20:17
 */
@Service(value = "shareCmsService")
public class ShareUrlCmsServiceImpl implements ShareUrlCmsService {

    @Autowired
    private ShareUrlCmsRepository shareUrlCmsRepository;

    @Transactional
    @Override
    public void add(String utlName,String url,String cid){
        ShareUrl shareUrl =new ShareUrl(Utils.getShortUUID(),utlName,url,new Date(),cid);
        shareUrlCmsRepository.save(shareUrl);
    }
    @Override
    public List<ShareUrl> list(){return shareUrlCmsRepository.findAll(); }

    @Transactional
    @Override
    public void upd(ShareUrl shareUrl,String name,String url,String cid){
        shareUrl.setUrlName(name);
        shareUrl.setUrl(url);
        shareUrl.setCreateId(cid);
        shareUrl.setCreateTime(new Date());
        shareUrlCmsRepository.save(shareUrl);
    }

    @Transactional
    @Override
    public void del(ShareUrl shareUrl){shareUrlCmsRepository.delete(shareUrl);}
}
