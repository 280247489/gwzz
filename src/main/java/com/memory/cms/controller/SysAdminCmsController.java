package com.memory.cms.controller;

import com.memory.entity.jpa.SysAdmin;
import com.memory.cms.service.SysAdminCmsService;
import com.memory.common.utils.Result;
import com.memory.common.utils.ResultUtil;
import com.memory.common.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author INS6+
 * @date 2019/5/7 16:26
 */

@RestController
@CrossOrigin
@RequestMapping(value = "sysAdmin")
public class SysAdminCmsController {

    @Autowired
    private SysAdminCmsService sysAdminService;

    /**
     * 用户登录
     * @param loginName
     * @param pwd
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Result login(@RequestParam("loginName") String loginName,@RequestParam("pwd") String pwd){
        Result result = new Result();
        try {

            SysAdmin sysAdmin =  sysAdminService.querySysAdminByLoginName(loginName);
            System.out.println(" login  ==== " + loginName);
            System.out.println(" pwd  ==== " + pwd);

            //登录成功
            if(sysAdmin != null && sysAdmin.getPassword().equals(Utils.md5Password(pwd))){
                //未被冻结
                if(sysAdmin.getNologin() != 1){
                    //返回值不携带密码
                    Map<String,Object> resultObj = new HashMap<String,Object>();
                    resultObj.put("id",sysAdmin.getId());
                    resultObj.put("loginname",sysAdmin.getLoginname());
                    resultObj.put("logo",sysAdmin.getLogo());
                    resultObj.put("name",sysAdmin.getName());
                    resultObj.put("sex",sysAdmin.getSex());
                    resultObj.put("birthday",sysAdmin.getBirthday());
                    resultObj.put("tel",sysAdmin.getTel());
                    resultObj.put("email",sysAdmin.getEmail());
                    resultObj.put("address",sysAdmin.getAddress());

                    result.setCode(0);
                    result.setMsg("登录成功!");
                    result.setData(resultObj);
                }else{
                    result.setCode(1);
                    result.setMsg("用户已被冻结！");
                    result.setData("");
                }

            }else{
                result.setCode(1);
                result.setMsg("用户名不存在或密码错误");
                result.setData("");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    };

    @RequestMapping(value = "options", method = RequestMethod.POST)
    public Result getAdminOptions(){
        Result result = new Result();
        try {
            List<Object> list = new ArrayList<>();
            Map<String,Object> mapper = null;
            List<SysAdmin> sysAdminList = sysAdminService.getSysAdminList();
            for (SysAdmin sysAdmin : sysAdminList) {
                mapper =new HashMap<>();
                mapper.put("id",sysAdmin.getId());
                mapper.put("name",sysAdmin.getName());
                list.add(mapper);
            }
            result = ResultUtil.success(list);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }



}
