package com.yiwugou.reb.service.test;

/**
 * 
 * @author zhanxiaoyong@yiwugou.com
 * 
 * @since 2016年6月23日 上午11:19:37
 */
public class DemoProvider {

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "test");
        System.setProperty("dubbo.spring.config", "classpath*:conf/spring/*.xml");
        com.alibaba.dubbo.container.Main.main(args);
    }
}