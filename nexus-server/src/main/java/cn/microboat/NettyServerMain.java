package cn.microboat;

import cn.microboat.annotation.RpcScan;
import cn.microboat.config.RpcServiceConfig;
import cn.microboat.remoting.transport.netty.server.NettyRpcServer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 扫描 "cn.microboat" 下的 RPC 注解
 *
 * @author zhouwei
 */
@RpcScan(basePackage = {"cn.microboat"})
public class NettyServerMain {
    public static void main(String[] args) {
        // Register service via annotation
        // 通过注解注册服务
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(NettyServerMain.class);
        NettyRpcServer nettyRpcServer = (NettyRpcServer) applicationContext.getBean("nettyRpcServer");

        // Register service manually
        // 手动注册服务
        HelloService helloService2 = new HelloService2Impl();
        RpcServiceConfig rpcServiceConfig = RpcServiceConfig.builder()
                .group("test2").version("version2").service(helloService2).build();

        nettyRpcServer.registerService(rpcServiceConfig);
        nettyRpcServer.start();
    }
}
