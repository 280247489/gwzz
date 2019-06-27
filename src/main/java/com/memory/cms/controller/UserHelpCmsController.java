package com.memory.cms.controller;

import com.memory.cms.service.UserHelpCmsService;
import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.UserHelp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName UserHelpCmsController
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/12 17:57
 */
@RestController
@RequestMapping(value = "userHelp/cms")
public class UserHelpCmsController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(UserHelpCmsController.class);

    @Autowired
    private UserHelpCmsService userHelpCmsService;

    @Autowired
    private DaoUtils daoUtils;

    /**
     * 添加用户帮助
     * URL:192.168.1.185:8081/gwzz/userHelp/cms/addUserHelp
     * @param helpTitle String 标题
     * @param helpSubtitle String 副标题
     * @param helpType int 类型
     * @param helpSort int 排序
     * @param createId String 创建人
     * @return
     */
    @RequestMapping(value = "addUserHelp",method = RequestMethod.POST)
    public Message addUsreHelp(@RequestParam String helpTitle, @RequestParam String helpSubtitle, @RequestParam Integer helpType,
                               @RequestParam Integer helpSort, @RequestParam String createId, MultipartFile helpLogo,MultipartFile helpContent){
        msg = Message.success();
        try {
            UserHelp userHelp = userHelpCmsService.checkHelpTitle(helpTitle,"");
            if (userHelp == null){
                userHelpCmsService.add(helpTitle, helpSubtitle, helpType, helpSort, createId, helpLogo,helpContent);
                msg.setRecode(0);
                msg.setMsg("添加成功");
            }else {
                msg.setRecode(1);
                msg.setMsg("该标题已存在");
            }
        }catch (Exception e){
            e.printStackTrace();
            msg.setRecode(1);
            msg.setMsg("系统错误");
            log.error("异常信息");
        }
        return msg;
    }

    /**
     * 查询用户帮助
     * URL:192.168.1.185:8081/gwzz/userHelp/cms/listUserHelp
     * @param page
     * @param size
     * @param direction
     * @param sorts
     * @param heleTitle String 标题
     * @param useYn  int 是否显示
     * @return list
     */
    @RequestMapping(value = "listUserHelp", method = RequestMethod.POST)
    public Message listUserHelp(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,
                                    @RequestParam(defaultValue = "desc") String direction, @RequestParam(defaultValue = "helpUpdateTime") String sorts,
                                    @RequestParam String heleTitle, @RequestParam Integer useYn) {
        msg = Message.success();
        try {
            List sortList =new ArrayList();
            Sort.Order order1 = new Sort.Order(Sort.Direction.ASC,"useYn");
            Sort.Order order2 = new Sort.Order(Sort.Direction.ASC,"helpSort");
            Sort.Order order3 = new Sort.Order(Sort.Direction.DESC,"helpUpdateTime");
            Sort.Order order4 = new Sort.Order(Sort.Direction.DESC,"helpCreateTime");
            sortList.add(order1);
            sortList.add(order2);
            sortList.add(order3);
            sortList.add(order4);
            Sort sort =new Sort(sortList);
            Pageable pageable = PageRequest.of(page, size,sort);

            Page<UserHelp> list = userHelpCmsService.findUserHelp(pageable,heleTitle,useYn);
            msg.setRecode(0);
            msg.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            msg.setRecode(1);
            msg.setMsg("系统错误");
            log.error("异常信息");
        }
        return msg;
    }

    /**
     * 根据id查询详情
     * URL:192.168.1.185:8081/gwzz/userHelp/cms/getById
     * @param id String 唯一标识
     * @return userHelp
     */
    @RequestMapping(value = "getById", method = RequestMethod.POST)
    public Message getById(@RequestParam String id){
        msg = Message.success();
        try {
            UserHelp userHelp = (UserHelp) daoUtils.getById("UserHelp", id);
            if (userHelp!=null){
                msg.setRecode(0);
                msg.setData(userHelp);
            }else {
                msg.setRecode(3);
                msg.setMsg("无此信息");
            }
        }catch (Exception e) {
            e.printStackTrace();
            msg.setRecode(1);
            msg.setMsg("系统错误");
            log.error("异常信息");
        }

        return msg;
    }
    /**
     * 修改用户帮助信息
     * URL:192.168.1.185:8081/gwzz/userHelp/cms/updUserHelp
     * @param id String 唯一标识ID
     * @param helpTitle String 标题
     * @param helpSubtitle String 副标题
     * @param helpType String 类型
     * @param helpSort String 排序
     * @param createId String 修改人Id
     * @return
     */
    @RequestMapping(value = "updUserHelp", method = RequestMethod.POST)
    public Message updUsreHelp(@RequestParam String id,@RequestParam String helpTitle, @RequestParam String helpSubtitle,
                               @RequestParam Integer helpType, @RequestParam Integer helpSort,String createId, MultipartFile helpLogo,MultipartFile helpContent){
        msg = Message.success();
        try {
            UserHelp userHelp = userHelpCmsService.checkHelpTitle(helpTitle,id);
            if (userHelp!=null){
                msg.setRecode(2);
                msg.setMsg("该标题已存在");
            }else{
                userHelp = userHelpCmsService.getUserHelpById(id);
                if(userHelp==null){
                    msg.setRecode(2);
                    msg.setMsg("非法id");
                }else{
                    UserHelp returnUserHelp =   userHelpCmsService.upd(userHelp,helpTitle,helpSubtitle,helpType,helpSort,createId,helpLogo,helpContent);
                    if(returnUserHelp!=null){
                        msg.setRecode(0);
                        msg.setMsg("修改成功");
                    }else {
                        msg.setRecode(1);
                        msg.setMsg("数据库保持失败");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            msg.setRecode(1);
            msg.setMsg("系统错误");
            log.error("异常信息");
        }
        return msg;
    }

    /**
     * 设置是否显示
     * URL:192.168.1.185:8081/gwzz/userHelp/cms/updUseYn
     * @param id String 帮助唯一标识Id
     * @return
     */
    @RequestMapping(value = "updUseYn", method = RequestMethod.POST)
    public Message updUseYn(@RequestParam String id,@RequestParam String operator_id){
        msg = Message.success();
        try {
            UserHelp userHelp = (UserHelp) daoUtils.getById("UserHelp",id);
            if (userHelp==null){
                msg.setRecode(2);
                msg.setMsg("该信息不存在");
            }else{
                UserHelp returnUserHelp =  userHelpCmsService.upduseYn(userHelp,operator_id);
                msg.setRecode(0);
                msg.setMsg("修改成功");
                msg.setData(returnUserHelp.getUseYn());
            }
        }catch (Exception e){
            e.printStackTrace();
            msg.setRecode(1);
            msg.setMsg("系统错误");
            log.error("异常信息");
        }
        return msg;
    }

    /**
     * 删除用户帮助
     * URL:192.168.1.185:8081/gwzz/userHelp/cms/delUserHelp
     * @param id String 用户中心唯一标识
     * @return
     */
    @RequestMapping(value = "delUserHelp", method = RequestMethod.POST)
    public Message delUserHelp(@RequestParam String id){
        msg = Message.success();
        try {
            UserHelp userHelp = (UserHelp) daoUtils.getById("UserHelp",id);
            if (userHelp==null){
                msg.setRecode(2);
                msg.setMsg("该信息不存在");
            }else{
                userHelpCmsService.del(userHelp);
                msg.setRecode(0);
                msg.setMsg("删除成功");
            }

        }catch (Exception e){
            e.printStackTrace();
            msg.setRecode(1);
            msg.setMsg("系统错误");
            log.error("异常信息");
        }
        return msg;
    }

}
