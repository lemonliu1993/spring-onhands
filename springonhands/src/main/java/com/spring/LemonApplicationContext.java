package com.spring;

/**
 * Created by lemoon on 2025/3/2 21:19
 */
public class LemonApplicationContext {

    private Class configClass;

    public LemonApplicationContext(Class configClass) {
        this.configClass = configClass;

        //在构造方法中做什么事呢？
        //解析配置类
        //ComponentScan注解 -->扫描路径 -->扫描
    }

    public Object getBean(String beanName){
        return null;
    }

}
