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
   private static final String ATTACHMENT_FILENAME = "attachment;filename=";

    private static final String XLSX = ".xlsx";

    private static final String CONTENT_DISPOSITION = "Content-disposition";

    private static final String IO_ERROR = "io error {}";

    /**
     * 从单对象数据集合中获取byte数组形式的一个单sheet的excel
     *
     * @param datas 单对象数据集合
     * @param clazz 数据对象类型
     * @return byte[]
     */
    public static <T> byte[] getOneSheetExcelOfByteArray(List<T> datas, Class<T> clazz) {
        if (datas == null) {
            return new byte[0];
        }
        // 输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ExcelWriter excelWriter = EasyExcel.write(out).build();
        writeDataToOutputStream(datas, excelWriter, clazz);
        excelWriter.finish();
        return out.toByteArray();
    }

    /**
     * 从单对象数据集合中获取inputstream形式的一个单sheet的excel
     *
     * @param datas 单对象数据集合
     * @param clazz 对象类型
     * @return InputStream
     */
    public static <T> InputStream getOneSheetExcelOfInputstream(List<T> datas, Class<T> clazz) {
        return new ByteArrayInputStream(getOneSheetExcelOfByteArray(datas, clazz));
    }

    /**
     * 从多对象数据集合中获取byte数组形式的一个单sheet的excel
     *
     * @param datasList 多对象数据集合
     * @return InputStream
     */
    public static byte[] getManySheetExcelOfByteArray(List<List< ?>> datasList) {
        if (CollectionUtils.isEmpty(datasList)) {
            return new byte[0];
        }
        // 输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ExcelWriter excelWriter = EasyExcel.write(out).build();
        datasList.forEach(datas -> writeDatasToOutputStream(datas, excelWriter));
        excelWriter.finish();
        return out.toByteArray();
    }

    /**
     * 从多对象数据集合中获取inputstream形式的一个单sheet的excel
     *
     * @param datasList 多对象数据集合
     * @return InputStream
     */
    public static InputStream getManySheetExcelOfInputStream(List<List< ?>> datasList) {
        return new ByteArrayInputStream(getManySheetExcelOfByteArray(datasList));
    }

    /**
     * 从单对象数据集合中获取一个单sheet的excel写入httpResponse响应
     *
     * @param fileName 文件名
     * @param datas 单对象数据集合
     * @param response 响应体
     * @param clazz 数据对象类型
     */
    public static <T> void getOneSheetExcelOfHttpResponse(String fileName, List<T> datas, HttpServletResponse response,
            Class<T> clazz) {
        response.setCharacterEncoding(CodingConstants.UTF_8);
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            String finalFileName = URLEncoder.encode(fileName, CodingConstants.UTF_8);
            response.setHeader(CONTENT_DISPOSITION, ATTACHMENT_FILENAME + finalFileName + XLSX);
            ExcelWriter excelWriter = EasyExcel.write(outputStream).build();
            writeDataToOutputStream(datas, excelWriter, clazz);
            excelWriter.finish();
        } catch (IOException e) {
            log.error(IO_ERROR, e.getMessage());
        }
    }

    /**
     * 从多对象数据集合中获取一个多sheet的excel写入httpResponse响应
     *
     * @param fileName 文件名
     * @param datasList 数据集合
     * @param response 响应体
     */
    public static void getManySheetExcelOfHttpResponse(String fileName, List<List< ?>> datasList,
            HttpServletResponse response) {
        if (CollectionUtils.isEmpty(datasList)) {
            return;
        }
        response.setCharacterEncoding(CodingConstants.UTF_8);
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            String finalFileName = URLEncoder.encode(fileName, CodingConstants.UTF_8);
            response.setHeader(CONTENT_DISPOSITION, ATTACHMENT_FILENAME + finalFileName + XLSX);
            ExcelWriter excelWriter = EasyExcel.write(outputStream).build();
            datasList.forEach(datas -> writeDatasToOutputStream(datas, excelWriter));
            excelWriter.finish();
        } catch (IOException e) {
            log.error(IO_ERROR, e.getMessage());
        }
    }

    /**
     * 将数据写入输出流
     *
     * @param datas 数据集合
     * @param excelWriter excelWriter实例
     * @param clazz 数据类型
     */
    private static <T> void writeDataToOutputStream(List<T> datas, ExcelWriter excelWriter, Class<T> clazz) {
        if (datas == null) {
            return;
        }
        // 获取类
        SheetNameAnnotation annotation = clazz.getAnnotation(SheetNameAnnotation.class);
        // 获取sheet名称
        String sheetName = annotation == null ? null : annotation.value();
        // 将数据写入输出流
        WriteSheet sheet = EasyExcel.writerSheet(sheetName)
                .head(clazz)
                .registerConverter(new LocalDateTimeConverter())
                .registerConverter(new LocalDateConverter())
                .registerWriteHandler(new CustomCellWeightHandler())
                .registerWriteHandler(new CustomCellStyleHandler())
                .registerWriteHandler(new CustomCellHeightHandler())
                .build();
        excelWriter.write(datas, sheet);
    }

    /**
     * 将数据写入输出流
     *
     * @param datas 数据集合
     * @param excelWriter excelWriter实例
     */
    private static void writeDatasToOutputStream(List< ?> datas, ExcelWriter excelWriter) {
        if (CollectionUtils.isEmpty(datas)) {
            return;
        }
        Class< ?> clazz = datas.get(0).getClass();
        // 获取类
        SheetNameAnnotation annotation = clazz.getAnnotation(SheetNameAnnotation.class);
        // 获取sheet名称
        String sheetName = annotation == null ? null : annotation.value();
        // 将数据写入输出流
        WriteSheet sheet = EasyExcel.writerSheet(sheetName)
                .head(clazz)
                .registerConverter(new LocalDateTimeConverter())
                .registerConverter(new LocalDateConverter())
                .registerWriteHandler(new CustomCellWeightHandler())
                .registerWriteHandler(new CustomCellStyleHandler())
                .registerWriteHandler(new CustomCellHeightHandler())
                .build();
        excelWriter.write(datas, sheet);
    }

    /**
     * 解析excel（简单表头,无数据校验）
     *
     * @param clazz model类
     * @param inputStream 输入流
     * @return ParseExcelResult
     */
    public static ParseExcelResult parseSimpleExcel(Class clazz, InputStream inputStream) {
        EasyExcelCommonListener listener = new EasyExcelCommonListener(clazz);
        return parseExcelAndSetResult(clazz, listener, inputStream);
    }

    /**
     * 解析excel（简单表头，可自定义数据校验）
     *
     * @param clazz model类
     * @param inputStream 输入流
     * @return ParseExcelResult
     */
    public static ParseExcelResult parseSimpleExcelWithDataVerification(Class clazz, EasyExcelCommonListener listener,
            InputStream inputStream) {
        listener.settClass(clazz);
        return parseExcelAndSetResult(clazz, listener, inputStream);
    }

    /**
     * 解析excel并设置结果
     *
     * @param clazz 对应实体类
     * @param listener 自定义listener
     * @param inputStream 文件流
     * @return ParseExcelResult
     */
    private static ParseExcelResult parseExcelAndSetResult(Class clazz, EasyExcelCommonListener listener,
            InputStream inputStream) {
        ParseExcelResult result = new ParseExcelResult();
        EasyExcel.read(inputStream, clazz, listener).sheet(0).doRead();
        result.setIsSuccess(listener.getSuccess());
        result.setDatas(listener.getDatas());
        result.setMessage(listener.getMessage());
        return result;
    }

    /**
     * 生成sheet（根据对象属性上注解表头信息）
     *
     * @param datas 数据集合
     * @param excelWriter excelWriter实例
     */
    public static void generateSheetByObjectAttrAnnotation(List< ?> datas, ExcelWriter excelWriter) {
        if (CollectionUtils.isEmpty(datas)) {
            return;
        }
        // 获取类
        Class< ?> dataClass = datas.get(0).getClass();
        SheetNameAnnotation annotation = dataClass.getAnnotation(SheetNameAnnotation.class);
        String sheetName = annotation == null ? null : annotation.value();
        WriteSheet sheet = EasyExcel.writerSheet(sheetName).head(dataClass).build();
        excelWriter.write(datas, sheet);
    }

    /* 动态合并表头多sheet excel生成相关 */

    /**
     * 生成excelWriter
     *
     * @param fileName 文件名
     * @param response 响应体
     * @return ExcelWriter
     */
    public static ExcelWriter generateExcelWriter(String fileName, HttpServletResponse response) {
        response.setCharacterEncoding(CodingConstants.UTF_8);
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            String finalFileName = URLEncoder.encode(fileName, CodingConstants.UTF_8);
            response.setHeader(CONTENT_DISPOSITION, ATTACHMENT_FILENAME + finalFileName + XLSX);
            return EasyExcel.write(outputStream).build();
        } catch (IOException e) {
            log.error(IO_ERROR, e.getMessage());
        }
        return EasyExcel.write().build();
    }

    /**
     * 生成sheet(根据动态表头数据)
     *
     * @Author: zhangjunqiang
     * @param sheetNameStr sheet名称
     * @param headMap 表头map
     * @return WriteSheet
     */
    public static void generateSheetByHeadMap(String sheetNameStr, LinkedHashMap<String, List<String>> headMap,
            List<List<String>> dataList, ExcelWriter excelWriter) {
        String sheetName = StringUtils.isEmpty(sheetNameStr) ? null : sheetNameStr;
        WriteSheet sheet = EasyExcel.writerSheet(sheetName).head(generateHead(headMap)).build();
        excelWriter.write(dataList, sheet);
    }

    /**
     * 生成动态表头数据
     *
     * @Author: zhangjunqiang
     * @param headMap 表头map
     * @return List<List<String>>
     */
    public static List<List<String>> generateHead(LinkedHashMap<String, List<String>> headMap) {
        List<List<String>> lists = new ArrayList<>();
        if (headMap == null) {
            return lists;
        }
        headMap.forEach((key, value) -> {
            if (CollectionUtils.isEmpty(value)) {
                return;
            }
            value.forEach(data -> {
                List<String> list = new ArrayList<>();
                list.add(key);
                list.add(data);
                lists.add(list);
            });
        });
        return lists;
    }

    /**
     * 释放资源
     *
     * @param response 响应体
     * @param excelWriter excel写入流
     */
    public static void releaseResource(HttpServletResponse response, ExcelWriter excelWriter) {
        excelWriter.finish();
        try {
            response.getOutputStream().close();
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }

    /**
     * 生成表头（多行表头）
     *
     * @param headRowNames 多少行表头，就有多少个集合  从表头第一行 到 表头最后一行
     * @return List<List<String>>
     */
    public static List<List<String>> generateMultipartHead(List<String>... headRowNames) {
        if (headRowNames.length == 0) {
            return new ArrayList<>();
        }
        if (headRowNames[0].size() == 0) {
            return new ArrayList<>();
        }
        // 列
        List<List<String>> headList = new ArrayList<>();
        for (int j = 0; j < headRowNames[0].size(); j++) {
            List<String> headCols = new ArrayList<>();
            // 行
            for (List<String> headRowName : headRowNames) {
                headCols.add(headRowName.get(j));
            }
            headList.add(headCols);
        }
        return headList;
    }

}
