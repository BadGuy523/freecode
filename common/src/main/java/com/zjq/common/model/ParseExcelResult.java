package com.zjq.common.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
  * @Description: 解析excel返回结果模型
  * @Author: zhangjunqiang
  * @Date: 2021/3/7 19:36
  * @version v1.0
  */
@Data
public class ParseExcelResult {

    /** 是否成功 */
    private Boolean isSuccess;

    /** 解析错误信息 */
    private Map<String, List<String>> message = new HashMap<>();

    /** 解析后数据 */
    private List<List<?>> datas = new ArrayList<>();

}
