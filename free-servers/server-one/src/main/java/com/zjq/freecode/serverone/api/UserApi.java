package com.zjq.freecode.serverone.api;

import com.zjq.freecode.common.model.ParseExcelResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

/**
  * @Description: user测试api
  * @Author: zhangjunqiang
  * @Date: 2021/7/3 22:03
  * @version v1.0
  */
public interface UserApi {

    @RequestMapping(method = RequestMethod.GET,value = "/test")
    default ResponseEntity<Void> test() throws InterruptedException {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
