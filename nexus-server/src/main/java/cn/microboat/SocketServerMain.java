package cn.microboat;

import cn.microboat.config.RpcServiceConfig;
import cn.microboat.remoting.transport.socket.SocketRpcServer;

/**
 * @author zhouwei
 */
public class SocketServerMain {
    public static void main(String[] args) {
        // 获取 service
        HelloService helloService = new HelloServiceImpl();

        // 获取 SocketRpcServer
        SocketRpcServer socketRpcServer = new SocketRpcServer();

        // 获取 RpcServiceConfig
        RpcServiceConfig rpcServiceConfig = new RpcServiceConfig();
        rpcServiceConfig.setService(helloService);

        // 注册服务
        socketRpcServer.registerService(rpcServiceConfig);

        // 服务启动
        socketRpcServer.start();
    }
}
