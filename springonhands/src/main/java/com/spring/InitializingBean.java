package com.spring;

/**
 * Created by lemoon on 2025/3/9 16:22
 */
public interface InitializingBean {

    void afterPropertiesSet() throws Exception;
}
