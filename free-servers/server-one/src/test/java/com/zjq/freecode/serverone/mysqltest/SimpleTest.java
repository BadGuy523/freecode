//package com.zjq.freecode.serverone.mysqltest;
//
//import com.zjq.freecode.common.configuration.ThreadPool;
//import com.zjq.freecode.serverone.ServerOneRunner;
//import com.zjq.freecode.serverone.dao.UserDao;
//import com.zjq.freecode.serverone.entity.User;
//import com.zjq.freecode.serverone.service.UserService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.Future;
//import java.util.concurrent.ThreadPoolExecutor;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = ServerOneRunner.class)
//public class SimpleTest {
//
//    @Autowired
//    private UserDao userDao;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private ThreadPool executor;
//
//    /**
//     * 简单测试数据库连接
//     */
//    @Test
//    public void testOne() {
//        System.out.println(userDao.queryAll(new User()));
//    }
//
//    /**
//     * 测试数据库死锁问题（submit）
//     *
//     * @throws CloneNotSupportedException
//     */
//    @Test
//    public void testTwo() throws CloneNotSupportedException {
//        Future<?> submit = executor.submit(() -> {
//            try {
//                userService.testMysqlDeadLock();
//            } catch (CloneNotSupportedException e) {
//                e.printStackTrace();
//            }
//            return null;
//        });
//        try {
//            submit.get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 测试数据库死锁问题（execute），不成功，接口调用成功 TODO：待分析
//     *
//     * @throws CloneNotSupportedException
//     */
//    @Test
//    public void testThree() throws CloneNotSupportedException {
//        executor.execute(() -> {
//            try {
//                userService.testMysqlDeadLock();
//            } catch (CloneNotSupportedException e) {
//                e.printStackTrace();
//            }
//        });
//    }
//
//    /**
//     * 测试数据库死锁问题,同步执行，单条更新
//     *
//     * @throws CloneNotSupportedException
//     */
//    @Test
//    public void testFour() throws CloneNotSupportedException {
//        userService.testMysqlDeadLock();
//    }
//
//    /**
//     * 测试数据库死锁问题,同步执行，批量更新
//     *
//     * @throws CloneNotSupportedException
//     */
//    @Test
//    public void testFive() throws CloneNotSupportedException {
//        userService.testMysqlDeadLockBatchUpdate();
//    }
//
//    @Test
//    public void batchInsertData() {
//        userService.batchInsertData();
//    }
//
//}
