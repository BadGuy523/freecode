package com.zjq.common.annotation;

import java.lang.annotation.*;

/**
  * @Description: excel sheet页名称
  * @Author: zhangjunqiang
  * @Date: 2021/3/18 20:53
  * @version v1.0
  */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SheetNameAnnotation {

    String value();

}
