package com.lemon.service;

import com.spring.Autowired;
import com.spring.Component;
import com.spring.Scope;

/**
 * Created by lemoon on 2025/3/2 21:37
 */
@Component("userService")
@Scope("prototype")
public class UserService {

    @Autowired
    private OrderService orderService;

    public void test(){
        System.out.println(orderService);
    }

}
