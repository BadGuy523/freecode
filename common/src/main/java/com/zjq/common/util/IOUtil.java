package com.zjq.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

/**
  * @Description: 文件相关工具类
  * @Author: zhangjunqiang
  * @Date: 2021/3/18 21:15
  * @version v1.0
  */
@Slf4j
public class IOUtil {

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
        } finally {
            try {
                IOUtils.close(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    /**
     * @Description: 下载excel文件（inputstream）
     * @author zhangjunqiang
     * @param inputStream 输入流
     * @param response http响应
     * @date 2021/3/7 21:35
     */
    public static void downloadExcel(InputStream inputStream, HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            String fileName = URLEncoder.encode("template", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            //定义读取缓冲区
            byte buffer[] = new byte[1024];
            //定义读取长度
            int len;
            //循环读取
            while((len = inputStream.read(buffer))!=-1){
                outputStream.write(buffer,0,len);
            }
        } catch (IOException e) {
            log.error("IO异常{}", e.getMessage());
        } finally {
            try {
                IOUtils.close(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @Description: 下载excel文件（byte[]）
     * @author zhangjunqiang
     * @param bytes byte数组
     * @param response http响应
     * @date 2021/3/7 21:35
     */
    public static void downloadExcel(byte[] bytes, HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            String fileName = URLEncoder.encode("template", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            outputStream.write(bytes);
        } catch (IOException e) {
            log.error("IO异常{}", e.getMessage());
        }
    }
}
