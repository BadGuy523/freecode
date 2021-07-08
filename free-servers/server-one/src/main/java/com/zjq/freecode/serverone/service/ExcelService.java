package com.zjq.freecode.serverone.service;

import com.zjq.freecode.common.model.ParseExcelResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
  * @Description: excel服务接口
  * @Author: zhangjunqiang
  * @Date: 2021/3/7 20:28
  * @version v1.0
  */
public interface ExcelService {

    ParseExcelResult parseExcel(MultipartFile file);

    void downloadExcel(HttpServletResponse response);
}
