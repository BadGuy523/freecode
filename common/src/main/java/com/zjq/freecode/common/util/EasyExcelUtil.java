package com.zjq.freecode.common.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.zjq.freecode.common.annotation.SheetNameAnnotation;
import com.zjq.freecode.common.listener.EasyExcelCommonListener;
import com.zjq.freecode.common.model.ParseExcelResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

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
        byte[] bytes = IOUtil.convertInputStreamToByteArray(inputStream);
        // 获取sheet信息
        List<ReadSheet> readSheets = EasyExcel.read(IOUtil.convertByteArrayToInputStream(bytes)).build().excelExecutor().sheetList();
        // 遍历sheet
        readSheets.forEach(sheet -> {
            EasyExcelCommonListener listener = new EasyExcelCommonListener();
            // TODO:获取sheet表头并与其对应对象做字段比对
            String sheetName = sheet.getSheetName();
            Class clazz = sheetNameClassMap.get(sheetName);
            List<List<String>> head = sheet.getHead();
            // 解析对应sheet表的数据
            EasyExcel.read(IOUtil.convertByteArrayToInputStream(bytes),clazz,listener).sheet(sheetName).doRead();
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

    /*以上为读取excel，以下为写入excel*/

    /**
     * @Description: 从单对象数据集合中获取byte数组形式的一个单sheet的excel
     * @author zhangjunqiang
     * @param datas 单对象数据集合
     * @return byte[]
     * @date 2021/3/18 21:50
     */
    public static byte[] getOneSheetExcelOfByteArray(List<?> datas) {
        if (CollectionUtils.isEmpty(datas)) {
            return new byte[0];
        }
        // 输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ExcelWriter excelWriter = EasyExcel.write(out).build();
        writeDataToOutputStream(datas,excelWriter);
        excelWriter.finish();
        return out.toByteArray();
    }

    /**
     * @Description: 从单对象数据集合中获取inputstream形式的一个单sheet的excel
     * @author zhangjunqiang
     * @param datas 单对象数据集合
     * @return byte[]
     * @date 2021/3/18 21:50
     */
    public static InputStream getOneSheetExcelOfInputstream(List<?> datas) {
        return new ByteArrayInputStream(getOneSheetExcelOfByteArray(datas));
    }

    /**
     * @Description: 从多对象数据集合中获取byte数组形式的一个单sheet的excel
     * @author zhangjunqiang
     * @param datasList 多对象数据集合
     * @return InputStream
     * @date 2021/3/18 21:50
     */
    public static byte[] getManySheetExcelOfByteArray(List<List<?>> datasList) {
        if (CollectionUtils.isEmpty(datasList)) {
            return new byte[0];
        }
        // 输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ExcelWriter excelWriter = EasyExcel.write(out).build();
        datasList.forEach(datas -> writeDataToOutputStream(datas, excelWriter));
        excelWriter.finish();
        return out.toByteArray();
    }

    /**
     * @Description: 从多对象数据集合中获取inputstream形式的一个单sheet的excel
     * @author zhangjunqiang
     * @param datasList 多对象数据集合
     * @return InputStream
     * @date 2021/3/18 21:50
     */
    public static InputStream getManySheetExcelOfInputStream(List<List<?>> datasList) {
        return new ByteArrayInputStream(getManySheetExcelOfByteArray(datasList));
    }


    /**
     * @Description: 从单对象数据集合中获取一个单sheet的excel写入httpResponse响应
     * @author zhangjunqiang
     * @param datas 单对象数据集合
     * @return byte[]
     * @date 2021/3/18 21:50
     */
    public static void getOneSheetExcelOfHttpResponse(List<?> datas,HttpServletResponse response) {
        if (CollectionUtils.isEmpty(datas)) {
            return;
        }
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        try (ServletOutputStream outputStream = response.getOutputStream()){
            String fileName = URLEncoder.encode("template", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            ExcelWriter excelWriter = EasyExcel.write(outputStream).build();
            writeDataToOutputStream(datas, excelWriter);
            excelWriter.finish();
        } catch (IOException e) {
            log.error("IO异常{}", e.getMessage());
        }
    }

    /**
     * @Description: 从多对象数据集合中获取一个多sheet的excel写入httpResponse响应
     * @author zhangjunqiang
     * @param datasList 多对象数据集合
     * @return byte[]
     * @date 2021/3/18 21:50
     */
    public static void getManySheetExcelOfHttpResponse(List<List<?>> datasList,HttpServletResponse response) {
        if (CollectionUtils.isEmpty(datasList)) {
            return;
        }
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        try (ServletOutputStream outputStream = response.getOutputStream()){
            String fileName = URLEncoder.encode("template", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            ExcelWriter excelWriter = EasyExcel.write(outputStream).build();
            datasList.forEach(datas -> writeDataToOutputStream(datas, excelWriter));
            excelWriter.finish();
        } catch (IOException e) {
            log.error("IO异常{}", e.getMessage());
        }
    }

    /**
     * @Description: 将数据写入输出流
     * @author zhangjunqiang
     * @param datas 数据集合
     * @param excelWriter
     * @date 2021/3/18 22:25
     */
    private static void writeDataToOutputStream(List<?> datas,ExcelWriter excelWriter) {
        if (CollectionUtils.isEmpty(datas)) {
            return;
        }
        // 获取类
        Class<?> dataClass = datas.get(0).getClass();
        // 获取sheet名称
        String sheetName = dataClass.getAnnotation(SheetNameAnnotation.class).value();
        // 将数据写入输出流
        WriteSheet sheet = EasyExcel.writerSheet(sheetName).head(dataClass).build();
        excelWriter.write(datas, sheet);
    }

}
