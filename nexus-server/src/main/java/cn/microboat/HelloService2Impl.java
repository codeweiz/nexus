package cn.microboat;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhouwei
 */
@Slf4j
public class HelloService2Impl implements HelloService {

    // 静态代码块，用于初始化
    static {
        System.out.println("HelloServiceImpl2被创建");
    }

    @Override
    public String hello(Hello hello) {
        log.info("HelloServiceImpl2收到: {}.", hello.getMessage());
        String result = "Hello description is " + hello.getDescription();
        log.info("HelloServiceImpl2返回: {}.", result);
        return result;
    }
}
