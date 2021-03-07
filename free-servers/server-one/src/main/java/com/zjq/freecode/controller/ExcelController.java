package com.zjq.freecode.controller;

import com.zjq.common.model.ParseExcelResult;
import com.zjq.freecode.api.ExcelApi;
import com.zjq.freecode.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
  * @Description: excel控制层
  * @Author: zhangjunqiang
  * @Date: 2021/3/7 20:27
  * @version v1.0
  */
@RestController
public class ExcelController implements ExcelApi {

    @Autowired
    private ExcelService excelService;

    @Autowired
    protected HttpServletResponse response;

    @Override
    public ResponseEntity<ParseExcelResult> getExcelData(MultipartFile file) {
        ParseExcelResult result = excelService.parseExcel(file);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ParseExcelResult> downloadExcel() {
        excelService.downloadExcel(response);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
