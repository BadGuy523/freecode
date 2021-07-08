package com.zjq.freecode.common.util;

import java.util.ArrayList;
import java.util.List;

/**
  * @Description: 公共工具
  * @Author: zhangjunqiang
  * @Date: 2021/7/3 22:45
  * @version v1.0
  */
public class PublicUtil {

    public static <T> List<List<T>> splitListWithSize(Integer size,List<T> list) {
        List<List<T>> result = new ArrayList<>();
        int count = list.size() / size;
        for (int i = 0; i < count; i++) {
            result.add(list.subList(i * size,i * size + size));
        }
        return result;
    }

}
