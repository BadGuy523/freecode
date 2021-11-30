package com.zjq.freecode.common.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 解析excel返回结果模型
 * @Author: zhangjunqiang
 * @Date: 2021/11/18
 */
@Data
@Accessors(chain = true)
public class ParseExcelResult<T> {
    /** 是否成功 */
    private Boolean isSuccess;

    /** 解析错误信息 */
    private List<String> message;

    /** 解析后数据 */
    private List<T> datas;

}
