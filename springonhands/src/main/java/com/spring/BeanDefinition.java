package com.spring;

/**
 * Created by lemoon on 2025/3/5 07:24
 */
public class BeanDefinition {

    private Class clazz;
    private String scope;
    //bean的name不在BeanDefinition里面


    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
