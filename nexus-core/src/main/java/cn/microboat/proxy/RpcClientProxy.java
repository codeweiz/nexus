package cn.microboat.proxy;

import cn.microboat.config.RpcServiceConfig;
import cn.microboat.enums.RpcErrorMessageEnum;
import cn.microboat.enums.RpcResponseCodeEnum;
import cn.microboat.exception.RpcException;
import cn.microboat.remoting.dto.RpcRequest;
import cn.microboat.remoting.dto.RpcResponse;
import cn.microboat.remoting.transport.RpcRequestTransport;
import cn.microboat.remoting.transport.netty.client.NettyRpcClient;
import cn.microboat.remoting.transport.socket.SocketRpcClient;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * RPC 客户端代理
 *
 * @author zhouwei
 */
@Slf4j
public class RpcClientProxy implements InvocationHandler {

    /**
     * 接口名
     */
    private static final String INTERFACE_NAME = "interfaceName";

    /**
     * Used to send requests to the server.And there are two implementations: socket and netty
     * RPC 请求传输
     */
    private final RpcRequestTransport rpcRequestTransport;

    /**
     * RPC 服务配置
     */
    private final RpcServiceConfig rpcServiceConfig;

    /**
     * 有参构造
     *
     * @param rpcRequestTransport RPC 请求传输
     * @param rpcServiceConfig    RPC 服务配置
     */
    public RpcClientProxy(RpcRequestTransport rpcRequestTransport, RpcServiceConfig rpcServiceConfig) {
        this.rpcRequestTransport = rpcRequestTransport;
        this.rpcServiceConfig = rpcServiceConfig;
    }

    /**
     * 有参构造
     *
     * @param rpcRequestTransport RPC 请求传输
     */
    public RpcClientProxy(RpcRequestTransport rpcRequestTransport) {
        this.rpcRequestTransport = rpcRequestTransport;
        this.rpcServiceConfig = new RpcServiceConfig();
    }

    /**
     * get the proxy object
     * 根据 class 获取 代理对象
     *
     * @param clazz 类
     */
    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    /**
     * This method is actually called when you use a proxy object to call a method.
     * 当您使用代理对象调用方法时，实际上会调用此方法。
     * The proxy object is the object you get through the getProxy method.
     * 代理对象就是你通过getProxy方法得到的对象。
     */
    @SneakyThrows
    @SuppressWarnings("unchecked")
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        log.info("invoked method: [{}]", method.getName());
        // 建造 RpcRequest 实例
        RpcRequest rpcRequest = RpcRequest.builder().methodName(method.getName())
                .parameters(args)
                .interfaceName(method.getDeclaringClass().getName())
                .paramTypes(method.getParameterTypes())
                .requestId(UUID.randomUUID().toString())
                .group(rpcServiceConfig.getGroup())
                .version(rpcServiceConfig.getVersion())
                .build();

        // 初始化 RpcResponse<Object>
        RpcResponse<Object> rpcResponse = null;

        // 如果实现方式是 Netty，同步非阻塞
        if (rpcRequestTransport instanceof NettyRpcClient) {
            // 使用 CompletableFuture<> 包裹 RpcResponse<Object>
            CompletableFuture<RpcResponse<Object>> completableFuture = (CompletableFuture<RpcResponse<Object>>) rpcRequestTransport.sendRpcRequest(rpcRequest);
            rpcResponse = completableFuture.get();
        }

        // 如果实现方式是 Socket，同步阻塞
        if (rpcRequestTransport instanceof SocketRpcClient) {
            rpcResponse = (RpcResponse<Object>) rpcRequestTransport.sendRpcRequest(rpcRequest);
        }

        // 检查 rpcResponse, rpcRequest 无误后，返回 rpcResponse.getData()
        this.check(rpcResponse, rpcRequest);
        return rpcResponse.getData();
    }

    /**
     * 检查
     *
     * @param rpcRequest  RPC 请求
     * @param rpcResponse RPC 响应
     */
    private void check(RpcResponse<Object> rpcResponse, RpcRequest rpcRequest) {

        // 如果 RPC 响应为空
        if (rpcResponse == null) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_INVOCATION_FAILURE, INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());
        }

        // 如果 RPC 请求的请求 id 和 RPC 响应的请求 id 不一致
        if (!rpcRequest.getRequestId().equals(rpcResponse.getRequestId())) {
            throw new RpcException(RpcErrorMessageEnum.REQUEST_NOT_MATCH_RESPONSE, INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());
        }

        // 如果 RPC 响应的 code 为空 或者 RPC 响应的 code 不等于 RpcResponseCodeEnum.SUCCESS.getCode()
        if (rpcResponse.getCode() == null || !rpcResponse.getCode().equals(RpcResponseCodeEnum.SUCCESS.getCode())) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_INVOCATION_FAILURE, INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());
        }
    }
}
