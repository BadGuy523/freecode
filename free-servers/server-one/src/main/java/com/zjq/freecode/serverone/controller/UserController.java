package com.zjq.freecode.serverone.controller;

import com.zjq.freecode.common.configuration.ThreadPool;
import com.zjq.freecode.serverone.api.UserApi;
import com.zjq.freecode.serverone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
  * @Description: user测试controller
  * @Author: zhangjunqiang
  * @Date: 2021/7/3 22:03
  * @version v1.0
  */
@RestController
public class UserController implements UserApi {

    @Autowired
    private UserService userService;

    @Autowired
    private ThreadPool threadPool;

    @Override
    public ResponseEntity<Void> test() throws InterruptedException {
        userService.testMysqlDeadLockIndex();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
