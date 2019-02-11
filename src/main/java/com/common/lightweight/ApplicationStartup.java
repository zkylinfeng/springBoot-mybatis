/**
 * ApplicationStartup.java
 * Created by bocs on 2018/1/4.
 */
package com.common.lightweight;

import com.common.lightweight.configuration.DBInit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Copyright (c) BOC Services（Kunshan）Co.,Ltd.
 * @author  zouky
 * @date 2018/1/4
 * 实现spring boot程序在启动时在容器初始化完成后执行自定义的初始化代码，如将数据加载到redis
 */
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
    private static Logger logger = LoggerFactory.getLogger(ApplicationStartup.class);
    /**
     * 注入数据库初始化bean，在程序启动完成后执行数据库建表等操作
     */
    @Autowired
    DBInit dbInit;

    /**
     * 自定义的初始化代码在该方法中实现
     * 不能直接使用注解注入的bean对象，会出现空指针异常，需要用代码方式从程序上下文容器中获取需要的bean
     * @param contextRefreshedEvent 是spring的ApplicationContextEvent的一个实现，在容器初始化完成后调用
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //数据库建表和基本数据初始化
        DBInit dbInit = contextRefreshedEvent.getApplicationContext().getBean(DBInit.class);
        if (dbInit != null) {
            logger.info("数据库初始化开始");
            dbInit.generateDB();
            logger.info("数据库初始化完成");
        }
    }
}
