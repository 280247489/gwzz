package com.memory.excel.imports;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.afterturn.easypoi.handler.inter.IExcelDataHandler;
import com.memory.cms.entity.CourseExtExcel;
import com.memory.excel.entity.ExcelResult;
import com.memory.excel.handler.CourseExtExcelHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/10 16:23
 */
@RestController
public class CourseExtExcelImport {



        private static final Logger log = LoggerFactory.getLogger(CourseExtExcelImport.class);
        @RequestMapping(value = "excel"/*, method = RequestMethod.POST*/)
        public ExcelResult excelImport(MultipartFile file) {
            ExcelResult excelResult = new ExcelResult();
            ImportParams importParams = new ImportParams();
            //importParams.setHeadRows(1);
           // importParams.setTitleRows(0);
            IExcelDataHandler<CourseExtExcel> handler = new CourseExtExcelHandler();
            handler.setNeedHandlerFields(new String[] { "内容类型","昵称" });// 注意这里对应的是excel的列名(注解指定的值)。也就是对象上指定的列名。
            importParams.setDataHanlder(handler);
            importParams.setNeedVerfiy(true);
            try {
                ExcelImportResult<CourseExtExcel> result = ExcelImportUtil.importExcelMore(file.getInputStream(), CourseExtExcel.class,
                        importParams);
                List<CourseExtExcel> successList = result.getList();
                List<CourseExtExcel> failList = result.getFailList();

                if(result.isVerfiyFail()){
                    excelResult.setSuccess(false);
                }
                excelResult.setList(successList);
                log.info("是否存在验证未通过的数据:" + result.isVerfiyFail());
                log.info("验证通过的数量:" + successList.size());
                 log.info("验证未通过的数量:" + failList.size());


            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            return  excelResult;
        }




}
