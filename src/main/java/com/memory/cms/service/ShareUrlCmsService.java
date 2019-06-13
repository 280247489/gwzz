package com.memory.cms.service;

import com.memory.entity.jpa.ShareUrl;

import java.util.List;

/**
 * @ClassName ShareUrlCmsService
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/12 20:17
 */
public interface ShareUrlCmsService {
    void add(String utlName,String url,String cid);
    List<ShareUrl> list();
    void upd(ShareUrl shareUrl,String name,String url,String cid);
    void del(ShareUrl shareUrl);
}
