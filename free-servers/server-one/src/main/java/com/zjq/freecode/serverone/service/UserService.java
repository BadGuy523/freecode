package com.zjq.freecode.serverone.service;

/**
  * @Description: userservice
  * @Author: zhangjunqiang
  * @Date: 2021/7/3 15:57
  * @version v1.0
  */
public interface UserService {

    /**
     * @Description: 测试mysql锁等待问题,单条更新
     * @author zhangjunqiang
     * @date 2021/7/3 16:01
     */
    void testMysqlDeadLock() throws CloneNotSupportedException;

    /**
     * @Description: 批量新增数据
     * @author zhangjunqiang
     * @date 2021/7/3 22:19
     */
    void batchInsertData();

    /**
     * @Description: 测试mysql锁等待问题，批量更新
     * @author zhangjunqiang
     * @date 2021/7/3 16:01
     */
    void testMysqlDeadLockBatchUpdate();

    /**
     * @Description: 测试mysql死锁问题
     * @author zhangjunqiang
     * @date 2021/7/4 16:04
     */
    void testMysqlDeadLockIndex() throws InterruptedException;
}
