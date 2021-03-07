package com.zjq.freecode.service.impl;

import com.zjq.common.model.ParseExcelResult;
import com.zjq.common.util.EasyExcelUtil;
import com.zjq.freecode.model.DemoData;
import com.zjq.freecode.model.DemoData2;
import com.zjq.freecode.service.ExcelService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
  * @Description: excel服务实现类
  * @Author: zhangjunqiang
  * @Date: 2021/3/7 20:28
  * @version v1.0
  */
@Service
public class ExcelServiceImpl implements ExcelService {
    @Override
    public ParseExcelResult parseExcel(MultipartFile file) {
        Map<String,Class> sheetNameClassMap = new HashMap<>();
        sheetNameClassMap.put("Sheet1", DemoData.class);
        sheetNameClassMap.put("Sheet2", DemoData2.class);
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EasyExcelUtil.parseExcel(sheetNameClassMap,inputStream);
    }

    @Override
    public void downloadExcel(HttpServletResponse response) {
        Map<String,Class> sheetNameClassMap = new HashMap<>();
        sheetNameClassMap.put("Sheet1", DemoData.class);
        sheetNameClassMap.put("Sheet2", DemoData2.class);

        Map<String, List<?>> sheetNameDataMap = new LinkedHashMap<>();
        List list = new ArrayList<>();
        DemoData demoData = new DemoData();
        demoData.setCost(12D);
        demoData.setMonth(12);
        demoData.setYear(2020);
        demoData.setName("tom");
        list.add(demoData);
        List list2 = new ArrayList<>();
        DemoData2 demoData2 = new DemoData2();
        demoData2.setCost(12D);
        demoData2.setMonth(12);
        demoData2.setYear(2020);
        demoData2.setName("tom2");
        list2.add(demoData2);
        sheetNameDataMap.put("Sheet1",list);
        sheetNameDataMap.put("Sheet2",list2);

        EasyExcelUtil.writeExcel(sheetNameDataMap,sheetNameClassMap,response);
    }
}
