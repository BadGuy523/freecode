package com.zjq.freecode.serverone.service.impl;

import com.zjq.freecode.common.configuration.ThreadPool;
import com.zjq.freecode.common.util.PublicUtil;
import com.zjq.freecode.serverone.dao.UserDao;
import com.zjq.freecode.serverone.entity.User;
import com.zjq.freecode.serverone.service.UserService;
import com.zjq.freecode.serverone.service.UserService2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
  * @Description: userservice实现
  * @Author: zhangjunqiang
  * @Date: 2021/7/3 15:58
  * @version v1.0
  */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ThreadPool threadPool;

    @Autowired
    private UserService2 userService2;

    /**
     * @Description: 测试mysql锁等待问题,单条更新
     *               并行流会在主线程的基础上，新开CPU核数-1的线程数执行update操作，属于主线程事务之外的事务
     *               若更新语句中存在索引条件，其他事务中的修改若存在修改相同行，则会拿不到行锁而阻塞
     *               若更新语句中不存在索引条件，其他事务会拿不到表锁而阻塞
     * @author zhangjunqiang
     * @date 2021/7/3 16:01
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void testMysqlDeadLock() throws CloneNotSupportedException {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            User user = new User(i+1,"badguy",26,"badguy", LocalDateTime.now().toString());
            users.add(user);
        }
        AtomicInteger count = new AtomicInteger(0);
        try {
            users.parallelStream().forEach(data -> {
                log.info(data.toString());
                userDao.update(data);
//                count.getAndSet(count.get() + 1); //此种写法是线程不安全的，因为count.get()方法线程不安全
                count.decrementAndGet(); //此处会调用unsafe.getAndAddInt，线程安全
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            log.info("共执行{}次",count.get());
        }
    }

    /**
     * 测试数据库锁等待问题,同步执行，批量更新
     *
     * @throws CloneNotSupportedException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void testMysqlDeadLockBatchUpdate() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 500000; i++) {
            User user = new User(i+1,"badguy",26,"badguy", LocalDateTime.now().toString());
            users.add(user);
        }
        List<List<User>> lists = PublicUtil.splitListWithSize(500, users);
        AtomicInteger count = new AtomicInteger(1);
        lists.parallelStream().forEach(list -> {
            log.info(String.valueOf(count.getAndIncrement()));
            userDao.batchUpdate(list);
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void testMysqlDeadLockIndex() throws InterruptedException {
        User user = new User(1,"badguy1",26,"badguy", LocalDateTime.now().toString());
        User user2 = new User(2,"badguy1",26,"badguy", LocalDateTime.now().toString());
        userDao.update(user);
        new Thread(() -> {
            try {
                userService2.testMysqlDeadLockIndex2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(1000);
        userDao.update(user2);

    }

    /**
     * @Description: 批量新增数据
     * @author zhangjunqiang
     * @date 2021/7/3 22:19
     */
    @Override
    public void batchInsertData() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 500000; i++) {
            User user = new User(i+1,"badguy",26,"badguy","523");
            users.add(user);
        }
        List<List<User>> lists = PublicUtil.splitListWithSize(1000, users);
        lists.parallelStream().forEach(list -> userDao.insertBatch(list));
    }
}
