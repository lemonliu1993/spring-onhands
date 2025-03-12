package com.lemon.service;

import com.spring.*;

/**
 * Created by lemoon on 2025/3/2 21:37
 */
@Component("userService")
@Scope("prototype")
public class UserService implements InitializingBean {

    @Autowired
    private OrderService orderService;

    private String beanName;

    private String name;

    public void test() {
        System.out.println(orderService);
        System.out.println(beanName);
        System.out.println(name);
    }

    public void setBeanName(String name) {
        beanName = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println("初始化");
    }
}
