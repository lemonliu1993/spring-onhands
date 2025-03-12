package com.spring;

/**
 * Created by lemoon on 2025/3/10 08:35
 */
public interface BeanPostProcessor {

    Object postPorcessorBeforeInitialization(Object bean,String beanName);


    Object postPorcessorAfterInitialization(Object bean,String beanName);

}
