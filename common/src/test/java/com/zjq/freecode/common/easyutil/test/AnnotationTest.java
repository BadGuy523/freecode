package com.zjq.freecode.common.easyutil.test;

import com.zjq.freecode.common.annotation.SheetNameAnnotation;

public class AnnotationTest {

    public static void main(String[] args) {
        Class<DemoTwo> demoOneClass = DemoTwo.class;
        SheetNameAnnotation annotation = demoOneClass.getAnnotation(SheetNameAnnotation.class);
        System.out.println(annotation.value());
    }

}
