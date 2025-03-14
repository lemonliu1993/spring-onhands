package com.lemon;

import com.lemon.service.UserService;
import com.spring.LemonApplicationContext;

/**
 * Created by lemoon on 2025/3/2 21:22
 */
public class Test {

    public static void main(String[] args) {
        LemonApplicationContext lemonApplicationContext = new LemonApplicationContext(AppConfig.class);


        System.out.println(lemonApplicationContext.getBean("userService"));
//        System.out.println(lemonApplicationContext.getBean("userService"));
//        System.out.println(lemonApplicationContext.getBean("userService"));

        UserService userService = (UserService)lemonApplicationContext.getBean("userService");
        userService.test();     //1.代理对象  2.业务test
//        Object userService = lemonApplicationContext.getBean("userService");    //map  <beanName,bean对象>
//        Object userService1 = lemonApplicationContext.getBean("userService");
//        Object userService2 = lemonApplicationContext.getBean("userService");
    }
}
