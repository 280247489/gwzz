package com.memory.cms.controller;

import com.memory.entity.jpa.ArticleType;
import com.memory.cms.service.ArticleTypeCmsService;
import com.memory.common.utils.Result;
import com.memory.common.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/9 16:31
 */
@RestController
@RequestMapping(value = "articleType")
public class ArticleTypeCmsController {

    @Autowired
    private ArticleTypeCmsService articleTypeService;

    @RequestMapping(value = "options"/*, method = RequestMethod.POST*/)
    public Result getArticleTypes(){
        Result result = new Result();
        try {
            List<ArticleType> list = articleTypeService.queryArticleTypeList();
            result = ResultUtil.success(list);

        }catch (Exception e){
            e.printStackTrace();
            result = ResultUtil.error(-1,"系统异常");
        }

        return result;
    }



}
