package com.memory.cms.service;

import com.memory.entity.jpa.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName BannerCmsService
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/11 18:12
 */
public interface BannerCmsService {
    void addBanner(String bName, MultipartFile bLogo, String typeTable, String typeTableId, Integer bannerSort, String createId);
    Banner checkBannerName(String bName, String id);
    Page<Banner> findBanner(Pageable pageable, String bName);
    Banner upd(Banner banner, String bName, MultipartFile bLogo, String typeTable, String typeTableId, Integer bannerSort, String createId);
    Banner updOnine(Banner banner);
    void del(Banner banner);
}
