package com.spring;

/**
 * Created by lemoon on 2025/3/2 21:19
 */
public class LemonApplicationContext {

    private Class configClass;

    public LemonApplicationContext(Class configClass) {
        this.configClass = configClass;
    }

    public Object getBean(String beanName){
        return null;
    }

}
