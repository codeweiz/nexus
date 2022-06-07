package cn.microboat;

import cn.microboat.annotation.RpcReference;
import org.springframework.stereotype.Component;

/**
 * Spring 注解，标注一个类为一个 Component
 *
 * @author zhouwei
 */
@Component
public class HelloController {

    /**
     * 注解 @RpcReference(version = "version1", group = "test1")
     * 标注在字段（实例）上，表示这个实例可进行 RPC 调用
     * 版本号为：version1
     * 群组为：test1
     */
    @RpcReference(version = "version1", group = "test1")
    private HelloService helloService;

    /**
     * 测试方法
     * 使用 helloService RPC 调用 hello 方法，参数为 new Hello("111", "222")
     */
    public void test() throws InterruptedException {
        String hello = this.helloService.hello(new Hello("111", "222"));
        // 如需使用 assert 断言，需要在 VM options 添加参数：-ea
        assert "Hello description is 222".equals(hello);
        Thread.sleep(12000);
        for (int i = 0; i < 10; i++) {
            System.out.println(helloService.hello(new Hello("111", "222")));
        }
    }
}
