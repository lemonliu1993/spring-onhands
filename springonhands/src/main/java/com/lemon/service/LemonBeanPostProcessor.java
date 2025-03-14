package com.lemon.service;

import com.spring.BeanPostProcessor;
import com.spring.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

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
            ((UserServiceImpl) bean).setName("lemon好帅");
        }
//        这里可以随便new 对象返回，AOP就是这么实现的
//        return new UserService();
        return bean;
    }

    public Object postPorcessorAfterInitialization(Object bean, String beanName) {
        System.out.println("初始化后");
        //可以判断是否需要执行aop，匹配
        if (beanName.equals("userService")) {
            //用jdk的话还得写一个接口
            Object proxyInstance = Proxy.newProxyInstance(LemonBeanPostProcessor.class.getClassLoader(),
                    bean.getClass().getInterfaces(),
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            System.out.println("代理逻辑");     //找切点
                            return method.invoke(bean, args);
                        }
                    });
            //如果是userService的时候，则返回一个代理对象
            return proxyInstance;

        }
        return bean;
    }
}
