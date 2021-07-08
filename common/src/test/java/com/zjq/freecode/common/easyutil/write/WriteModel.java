package com.zjq.freecode.common.easyutil.write;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zjq.freecode.common.annotation.SheetNameAnnotation;

@SheetNameAnnotation("测试sheet")
public class WriteModel {

    @ExcelProperty(value = {"单位","单位"},index = 0)
    private String name;

    @ExcelProperty(value = {"数据一","姓名"},index = 1)
    private String dataOneName;

    @ExcelProperty(value = {"数据一","年龄"},index = 2)
    private Integer dataOneAge;

    @ExcelProperty(value = {"数据二","姓名"},index = 3)
    private String dataTwoName;

    @ExcelProperty(value = {"数据二","年龄"},index = 4)
    private Integer dataTwoAge;

}
