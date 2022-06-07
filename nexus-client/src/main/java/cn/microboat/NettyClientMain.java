package cn.microboat;

import cn.microboat.annotation.RpcScan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 注解 @RpcScan(basePackage = {"cn.microboat"}) 标注在类上
 * 表示 RPC 扫描的包路径：{"cn.microboat"}
 * @author zhouwei
 */
@RpcScan(basePackage = {"cn.microboat"})
public class NettyClientMain {

    public static void main(String[] args) throws InterruptedException {
        // AnnotationConfigApplicationContext 注解配置应用上下文
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(NettyClientMain.class);
        // 获取 helloController bean实例
        HelloController helloController = (HelloController) applicationContext.getBean("helloController");
        // 调用 test 方法
        helloController.test();
    }
}
