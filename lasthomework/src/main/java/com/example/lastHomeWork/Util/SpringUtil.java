package com.example.lastHomeWork.Util;

import org.springframework.context.ApplicationContext;

import java.util.Arrays;
//辅助类，用来管理和使用bean。
public class SpringUtil {
    public static ApplicationContext applicationContext = null;
    //在main函数中将本项目的ApplicationContext设置到SpringUtil里面来。这样可以通过
    //SpringUtil来调用ApplicationContext；
    public  static void setApplicationContext(ApplicationContext ctx){
        SpringUtil.applicationContext = ctx;
    }
    public static void printBean(){
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }
}
