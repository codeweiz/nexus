package cn.microboat;

import cn.microboat.config.RpcServiceConfig;
import cn.microboat.proxy.RpcClientProxy;
import cn.microboat.remoting.transport.RpcRequestTransport;
import cn.microboat.remoting.transport.socket.SocketRpcClient;

/**
 * @author zhouwei
 */
public class SocketClientMain {

    public static void main(String[] args) {
        // SocketRpcClient 实例
        RpcRequestTransport rpcRequestTransport = new SocketRpcClient();
        // RpcServiceConfig 实例
        RpcServiceConfig rpcServiceConfig = new RpcServiceConfig();
        // RpcClientProxy 实例
        RpcClientProxy rpcClientProxy = new RpcClientProxy(rpcRequestTransport, rpcServiceConfig);
        // RpcClientProxy 实例获取代理
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        // 调用 hello 方法
        String hello = helloService.hello(new Hello("111", "222"));
        System.out.println(hello);
    }
}
