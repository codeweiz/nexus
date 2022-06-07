package cn.microboat.remoting.handler;

import cn.microboat.factory.SingletonFactory;
import cn.microboat.provider.ServiceProvider;
import cn.microboat.provider.impl.ZkServiceProviderImpl;
import cn.microboat.remoting.dto.RpcRequest;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author zhouwei
 */
@Slf4j
public class RpcRequestHandler {

    private final ServiceProvider serviceProvider;

    public RpcRequestHandler() {
        this.serviceProvider = SingletonFactory.getInstance(ZkServiceProviderImpl.class);
    }

    /**
     * 处理
     *
     * @param rpcRequest RPC请求
     * @return Object
     */
    public Object handle(RpcRequest rpcRequest) {
        Object service = serviceProvider.getService(rpcRequest.getRpcServiceName());
        return invokeTargetMethod(rpcRequest, service);
    }

    /**
     * 调用目标方法
     *
     * @param rpcRequest RPC请求
     * @param service    服务
     * @return Object
     */
    private Object invokeTargetMethod(RpcRequest rpcRequest, Object service) {
        Object result = null;
        try {
            Method method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
            result = method.invoke(service, rpcRequest.getParameters());
            log.info("service:[{}] successful invoke method:[{}]", rpcRequest.getInterfaceName(), rpcRequest.getMethodName());
        } catch (NoSuchMethodException | IllegalArgumentException | InvocationTargetException |
                 IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }
}
