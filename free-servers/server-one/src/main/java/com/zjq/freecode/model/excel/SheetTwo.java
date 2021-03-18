package com.zjq.freecode.model.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zjq.common.annotation.SheetNameAnnotation;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@SheetNameAnnotation("测试sheetTwo")
public class SheetTwo {

    @ExcelProperty(value = {"单位","单位"},index = 0)
    private String unit;

    @ExcelProperty(value = {"数据一","收入"},index = 1)
    private Double dataOneIn;

    @ExcelProperty(value = {"数据一","支出"},index = 2)
    private Double dataOneOut;

    @ExcelProperty(value = {"数据二","收入"},index = 3)
    private Double dataTwoIn;

    @ExcelProperty(value = {"数据二","支出"},index = 4)
    private Double dataTwoOut;

}
