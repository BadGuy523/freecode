package com.zjq.freecode.serverone.service.impl;

import com.zjq.freecode.serverone.dao.UserDao;
import com.zjq.freecode.serverone.entity.User;
import com.zjq.freecode.serverone.service.UserService2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
  * @Description: userservice实现
  * @Author: zhangjunqiang
  * @Date: 2021/7/3 15:58
  * @version v1.0
  */
@Slf4j
@Service
public class UserServiceImpl2 implements UserService2 {

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void testMysqlDeadLockIndex2() throws InterruptedException {
        User user = new User(1,"badguy1",26,"badguy", LocalDateTime.now().toString());
        User user2 = new User(2,"badguy1",26,"badguy", LocalDateTime.now().toString());
        userDao.update(user2);
        userDao.update(user);
    }
}
