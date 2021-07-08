package com.zjq.freecode.serverone.model.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zjq.freecode.common.annotation.SheetNameAnnotation;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@SheetNameAnnotation("测试sheetOne")
public class SheetOne {

    @ExcelProperty(value = {"人员","人员"},index = 0)
    private String person;

    @ExcelProperty(value = {"数据一","姓名"},index = 1)
    private String dataOneName;

    @ExcelProperty(value = {"数据一","年龄"},index = 2)
    private Integer dataOneAge;

    @ExcelProperty(value = {"数据二","姓名"},index = 3)
    private String dataTwoName;

    @ExcelProperty(value = {"数据二","年龄"},index = 4)
    private Integer dataTwoAge;

}
