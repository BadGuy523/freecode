package com.zjq.common.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.zjq.common.listener.EasyExcelCommonListener;
import com.zjq.common.model.ParseExcelResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
  * @Description: easyexcel工具类
  * @Author: zhangjunqiang
  * @Date: 2021/3/7 14:26
  * @version v1.0
  */
@Slf4j
public class EasyExcelUtil {

    /**
     * @Description: 解析excel
     * @author zhangjunqiang
     * @param sheetNameClassMap sheet名称和model类map映射关系
     * @param inputStream 输入流
     * @return List<List<?>>
     * @throws
     * @date 2021/3/7 19:03
     */
    public static ParseExcelResult parseExcel(Map<String,Class> sheetNameClassMap, InputStream inputStream) {
        ParseExcelResult result = new ParseExcelResult();
        byte[] bytes = convertInputStreamToByteArray(inputStream);
        // 获取sheet信息
        List<ReadSheet> readSheets = EasyExcel.read(convertByteArrayToInputStream(bytes)).build().excelExecutor().sheetList();
        // 遍历sheet
        readSheets.forEach(sheet -> {
            EasyExcelCommonListener listener = new EasyExcelCommonListener();
            // 获取sheet表头并与其对应对象做字段比对
            String sheetName = sheet.getSheetName();
            Class clazz = sheetNameClassMap.get(sheetName);
            List<List<String>> head = sheet.getHead();
            // 解析对应sheet表的数据
            EasyExcel.read(convertByteArrayToInputStream(bytes),clazz,listener).sheet(sheetName).doRead();
            List list = listener.getList();
            result.getDatas().add(list);
        });
        return result;
    }

    /**
     * @Description: 解析excel
     * @author zhangjunqiang
     * @param sheetNameClassMap sheet名称和model类map映射关系
     * @param file 文件
     * @return List<List<?>>
     * @throws
     * @date 2021/3/7 19:03
     */
    public static ParseExcelResult parseExcel(Map<String,Class> sheetNameClassMap, File file) {
        ParseExcelResult result = new ParseExcelResult();
        // 获取sheet信息
        List<ReadSheet> readSheets = EasyExcel.read(file).build().excelExecutor().sheetList();
        // 遍历sheet
        readSheets.forEach(sheet -> {
            EasyExcelCommonListener listener = new EasyExcelCommonListener();
            // 获取sheet表头并与其对应对象做字段比对
            String sheetName = sheet.getSheetName();
            Class clazz = sheetNameClassMap.get(sheetName);
            List<List<String>> head = sheet.getHead();
            // 解析对应sheet表的数据
            EasyExcel.read(file,clazz,listener).sheet(sheetName).doRead();
            List list = listener.getList();
            result.getDatas().add(list);
        });
        return result;
    }

    /**
     * @Description: 写入excel并下载
     * @author zhangjunqiang
     * @param sheetNameDataMap sheet名称和model数据map映射关系
     * @param sheetNameClassMap sheet名称和model类map映射关系
     * @param response http响应
     * @date 2021/3/7 21:35
     */
    public static void writeExcel(Map<String,List<?>> sheetNameDataMap, Map<String,Class> sheetNameClassMap, HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            String fileName = URLEncoder.encode("template", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            //新建ExcelWriter
            ExcelWriter excelWriter = EasyExcel.write(outputStream).build();
            sheetNameDataMap.forEach((key,value) -> {
                WriteSheet sheet = EasyExcel.writerSheet(key).head(sheetNameClassMap.get(key)).build();
                excelWriter.write(value,sheet);
            });
            //关闭流
            excelWriter.finish();
        } catch (IOException e) {
            log.error("导出异常{}", e.getMessage());
        }
    }

    /**
     * @Description: 将输入流转换为byte数组
     * @author zhangjunqiang
     * @param inputStream 输入流
     * @return byte[]
     * @date 2021/3/7 21:36
     */
    public static byte[] convertInputStreamToByteArray(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream ();
        try {
            IOUtils.copy(inputStream,outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }

    /**
     * @Description: 将byte数组转换为输入流
     * @author zhangjunqiang
     * @param bytes byte数组
     * @return InputStream
     * @date 2021/3/7 21:36
     */
    public static InputStream convertByteArrayToInputStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }

}
