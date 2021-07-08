package com.zjq.freecode.common.easyutil.read;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
  * @Description: 测试对象
  * @Author: zhangjunqiang
  * @Date: 2021/3/7 17:31
  * @version v1.0
  */
@Data
public class DemoData {
    @ExcelProperty(value = "年",index = 0)
    private Integer year;
    @ExcelProperty(value = "月",index = 1)
    private Integer month;
    @ExcelProperty(value = "姓名",index = 2)
    private String name;
    @ExcelProperty(value = "花费",index = 3)
    private Double cost;
}
