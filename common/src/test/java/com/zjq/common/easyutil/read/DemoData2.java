package com.zjq.common.easyutil.read;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
  * @Description: 测试对象2
  * @Author: zhangjunqiang
  * @Date: 2021/3/7 17:31
  * @version v1.0
  */
@Data
public class DemoData2 {
    @ExcelProperty(value = "年2",index = 0)
    private Integer year;
    @ExcelProperty(value = "月2",index = 1)
    private Integer month;
    @ExcelProperty(value = "姓名2",index = 2)
    private String name;
    @ExcelProperty(value = "花费2",index = 3)
    private Double cost;
}
