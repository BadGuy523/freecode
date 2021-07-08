package com.zjq.freecode.serverone.api;

import com.zjq.freecode.common.model.ParseExcelResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

/**
  * @Description: excel接口
  * @Author: zhangjunqiang
  * @Date: 2021/3/7 20:26
  * @version v1.0
  */
public interface ExcelApi {

    @RequestMapping(method = RequestMethod.POST,value = "/data")
    default ResponseEntity<ParseExcelResult> getExcelData(MultipartFile file) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(method = RequestMethod.GET,value = "/data")
    default ResponseEntity<ParseExcelResult> downloadExcel() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
