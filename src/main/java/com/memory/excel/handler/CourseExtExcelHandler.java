package com.memory.excel.handler;

import com.memory.cms.entity.CourseExtExcel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.afterturn.easypoi.handler.impl.ExcelDataHandlerDefaultImpl;
/**
 * @author INS6+
 * @date 2019/5/10 16:27
 */

public class CourseExtExcelHandler extends ExcelDataHandlerDefaultImpl<CourseExtExcel> {

    private static final Logger log = LoggerFactory.getLogger(CourseExtExcelHandler.class);


    @Override
    public Object importHandler(CourseExtExcel obj, String name, Object value) {
        log.info(name+":"+value);
        return super.importHandler(obj, name, value);
    }


}
