package com.zjq.freecode.common.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

/**
  * @Description: easyexcel公共Listener
  * @Author: zhangjunqiang
  * @Date: 2021/3/7 19:16
  * @version v1.0
  */
public class EasyExcelCommonListener <T> extends AnalysisEventListener<T> {

    private List<T> list = new ArrayList<>();

    /**
     * @Description: 对与每一条数据的处理
     * @author zhangjunqiang
     * @param data 一行数据转换成的对象
     * @date 2021/3/7 19:17
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }

    public List<T> getList() {
        return list;
    }
}
