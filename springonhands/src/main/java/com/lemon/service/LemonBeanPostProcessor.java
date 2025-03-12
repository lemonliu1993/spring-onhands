package com.lemon.service;

import com.spring.BeanPostProcessor;
import com.spring.Component;

/**
 * Created by lemoon on 2025/3/10 08:38
 * 因为你定义了这么一个类，spring也要扫描到它，所以要让Spring管理，它是一个特殊的Bean
 */

@Component
public class LemonBeanPostProcessor implements BeanPostProcessor {


    //BeanPostProcessor 是针对Spring 统一的，不会针对某一个bean，而是对所有的bean都会执行

    public Object postPorcessorBeforeInitialization(Object bean, String beanName) {

        System.out.println("初始化前");

        if (beanName.equals("userService")) {
            ((UserService) bean).setName("lemon好帅");
        }
//        这里可以随便new 对象返回，AOP就是这么实现的
//        return new UserService();
        return bean;
    }

    public Object postPorcessorAfterInitialization(Object bean, String beanName) {
        System.out.println("初始化后");
        return bean;
    }
}
