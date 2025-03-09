package com.lemon.service;

import com.spring.Autowired;
import com.spring.BeanNameAware;
import com.spring.Component;
import com.spring.Scope;

/**
 * Created by lemoon on 2025/3/2 21:37
 */
@Component("userService")
@Scope("prototype")
public class UserService implements BeanNameAware {

    @Autowired
    private OrderService orderService;

    private String beanName;

    public void test() {
        System.out.println(orderService);
        System.out.println(beanName);
    }

    public void setBeanName(String name) {
        beanName = name;
    }
}
