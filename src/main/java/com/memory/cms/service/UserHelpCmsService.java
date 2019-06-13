package com.memory.cms.service;

import com.memory.entity.jpa.UserHelp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName UserHelpCmsService
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/12 16:29
 */
public interface UserHelpCmsService {
    UserHelp checkHelpTitle(String helpTitle, String id);
    void add(String helpTitle, String helpSubtitle, Integer helpType, Integer helpSort,String createId, HttpServletRequest request);
    Page<UserHelp> findUserHelp(Pageable pageable, String helpTitle, Integer useYn);
    UserHelp upd(UserHelp userHelp,String helpTitle, String helpSubtitle, Integer helpType, Integer helpSort,String createId, HttpServletRequest request);
    UserHelp upduseYn (UserHelp userHelp);
    void del(UserHelp userHelp);


}
