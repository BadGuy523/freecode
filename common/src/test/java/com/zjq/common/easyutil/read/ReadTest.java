package com.zjq.common.easyutil.read;

import com.zjq.common.model.ParseExcelResult;
import com.zjq.common.util.EasyExcelUtil;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: easyexcel读测试类
 * @Author: zhangjunqiang
 * @Date: 2021/3/7 14:47
 * @version v1.0
 */
public class ReadTest {

    @Test
    public void myTest() throws IOException {
        File file = new File("C:\\Users\\BadGuy\\Desktop\\freecoderesource\\test.xlsx");
        InputStream inputStream = new FileInputStream(file);
        // sheet页和对象映射关系
        Map<String,Class> sheetNameClassMap = new HashMap<>();
        sheetNameClassMap.put("Sheet1",DemoData.class);
        sheetNameClassMap.put("Sheet2",DemoData2.class);
        ParseExcelResult parseExcelResult = EasyExcelUtil.parseExcel(sheetNameClassMap, file);
        inputStream.close();
    }

}
