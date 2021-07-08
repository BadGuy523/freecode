package com.zjq.freecode.serverone.service.impl;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zjq.freecode.common.model.ParseExcelResult;
import com.zjq.freecode.common.util.EasyExcelUtil;
import com.zjq.freecode.serverone.model.DemoData;
import com.zjq.freecode.serverone.model.DemoData2;
import com.zjq.freecode.serverone.model.excel.SheetOne;
import com.zjq.freecode.serverone.model.excel.SheetTwo;
import com.zjq.freecode.serverone.service.ExcelService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<List<?>> datasList = new ArrayList<>();
        List<SheetOne> sheetOnes = new ArrayList<>();
        SheetOne sheetOne = new SheetOne();
        sheetOne.setPerson("张三")
                .setDataOneAge(18)
                .setDataOneName("zhangsan")
                .setDataTwoAge(19)
                .setDataTwoName("tom");
        SheetOne sheetOne2 = new SheetOne();
        sheetOne2.setPerson("张三2")
                .setDataOneAge(182)
                .setDataOneName("zhangsan2")
                .setDataTwoAge(192)
                .setDataTwoName("tom2");
        sheetOnes.add(sheetOne);
        sheetOnes.add(sheetOne2);

        List<SheetTwo> sheetTwos = new ArrayList<>();
        SheetTwo sheetTwo = new SheetTwo();
        sheetTwo.setUnit("报表项一")
                .setDataOneIn(12D)
                .setDataOneOut(12.4D)
                .setDataTwoIn(14D);
        SheetTwo sheetTwo2 = new SheetTwo();
        sheetTwo2.setUnit("报表项二")
                .setDataOneIn(12D)
                .setDataOneOut(12.4D)
                .setDataTwoIn(14D)
                .setDataTwoOut(19.5423453452345234523459823769245867D);
        sheetTwos.add(sheetTwo);
        sheetTwos.add(sheetTwo2);

        // TODO:反射修改注解value值，需再研究研究：http://www.360doc.com/content/20/0317/15/52167404_899805307.shtml
        Field field = null;
        try {
            field = SheetTwo.class.getDeclaredField("unit");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
        InvocationHandler h = Proxy.getInvocationHandler(annotation);
        Field hField = null;
        try {
            hField = h.getClass().getDeclaredField("memberValues");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        hField.setAccessible(true);
        Map memberValues = null;
        try {
            memberValues = (Map) hField.get(h);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        memberValues.put("value", new String[]{"单位", "百万"});

        datasList.add(sheetOnes);
        datasList.add(sheetTwos);

        /*单个sheet*/
        // 直接下载
//        EasyExcelUtil.getOneSheetExcelOfHttpResponse(sheetOnes,response);
        // 先byte再下载
//        byte[] oneSheetExcelOfByteArray = EasyExcelUtil.getOneSheetExcelOfByteArray(sheetOnes);
//        IOUtil.downloadExcel(oneSheetExcelOfByteArray,response);
        // 先inputstream再下载
//        InputStream oneSheetExcelOfInputstream = EasyExcelUtil.getOneSheetExcelOfInputstream(sheetOnes);
//        IOUtil.downloadExcel(oneSheetExcelOfInputstream,response);

        /*多个sheet*/
        // 直接下载
        EasyExcelUtil.getManySheetExcelOfHttpResponse(datasList,response);
        // 先byte再下载
//        byte[] oneSheetExcelOfByteArray = EasyExcelUtil.getOneSheetExcelOfByteArray(sheetOnes);
//        IOUtil.downloadExcel(oneSheetExcelOfByteArray,response);
        // 先inputstream再下载
//        InputStream oneSheetExcelOfInputstream = EasyExcelUtil.getOneSheetExcelOfInputstream(sheetOnes);
//        IOUtil.downloadExcel(oneSheetExcelOfInputstream,response);
    }
}
