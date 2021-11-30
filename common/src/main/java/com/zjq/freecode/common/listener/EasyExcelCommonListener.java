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
/**
     * 数据类型
     */
    protected Class tClass;

    /**
     * 是否成功
     */
    protected Boolean isSuccess = true;

    /**
     * 错误信息集合
     */
    protected List<String> message = new ArrayList<>();

    /**
     * 数据集合
     */
    protected List<T> datas = new ArrayList<>();

    public EasyExcelCommonListener() {
    }

    public EasyExcelCommonListener(Class clazz) {
        this.tClass = clazz;
    }

    /**
     * 对每一条数据的处理
     *
     * @param data 每一条数据object
     * @param context 上下文
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        if (isSuccess) {
            datas.add(data);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // do nothing
    }

    /**
     * 校验表头
     *
     * @param headMap 表头map
     * @param context 上下文
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        int row = context.readRowHolder().getRowIndex() + 1;
        super.invokeHeadMap(headMap, context);
        if (tClass == null) {
            return;
        }
        List<String> allTitles = new ArrayList<>();
        List<Field> allFields = new ArrayList<>();
        addAllFields(tClass, allFields);
        allFields.forEach(field -> {
            ExcelProperty declaredAnnotation = field.getDeclaredAnnotation(ExcelProperty.class);
            if (declaredAnnotation != null) {
                String[] value = declaredAnnotation.value();
                allTitles.add(value[row - 1]);
            }
        });
        List<String> actualTitles = new ArrayList<>();
        for (int i = 0; i < headMap.size(); i++) {
            actualTitles.add(headMap.get(i));
        }
        for (int i = 0; i < allTitles.size(); i++) {
            if (i > actualTitles.size() - 1) {
                message.add("第" + row + "行，第" + (i + 1) + "列表头（" + allTitles.get(i) + "）缺失");
                isSuccess = false;
            } else if (!StringUtils.equals(actualTitles.get(i), allTitles.get(i))) {
                message.add("第" + row + "行，第" + (i + 1) + "列表头（" + actualTitles.get(i) + "）错误，应为（" + allTitles.get(i)
                        + "）");
                isSuccess = false;
            }
        }
    }

    private void addAllFields(Class clazz, List<Field> fieldList) {
        if (clazz != null) {
            fieldList.addAll(Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList()));
            addAllFields(clazz.getSuperclass(), fieldList);
        } else {
            return;
        }
    }

    public List<T> getDatas() {
        return datas;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public List<String> getMessage() {
        return message;
    }

    public EasyExcelCommonListener<T> settClass(Class tClass) {
        this.tClass = tClass;
        return this;
    }
}
