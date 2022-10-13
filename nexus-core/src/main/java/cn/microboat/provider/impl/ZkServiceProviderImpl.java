package cn.microboat.provider.impl;

import cn.microboat.config.RpcServiceConfig;
import cn.microboat.enums.RpcErrorMessageEnum;
import cn.microboat.exception.RpcException;
import cn.microboat.extension.ExtensionLoader;
import cn.microboat.provider.ServiceProvider;
import cn.microboat.registry.ServiceRegistry;
import cn.microboat.remoting.transport.netty.server.NettyRpcServer;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Zookeeper 实现 ServiceProvider
 *
 * @author zhouwei
 */
@Slf4j
public class ZkServiceProviderImpl implements ServiceProvider {

    /**
     * 服务 Map
     */
    private final Map<String, Object> serviceMap;

    /**
     * 已注册的服务
     * Set 做容器，String 为泛型
     */
    private final Set<String> registeredService;

    /**
     * 服务注册
     */
    private final ServiceRegistry serviceRegistry;

    /**
     * 无参构造
     */
    public ZkServiceProviderImpl() {
        this.serviceMap = new ConcurrentHashMap<>();
        this.registeredService = ConcurrentHashMap.newKeySet();
        this.serviceRegistry = ExtensionLoader.getExtensionLoader(ServiceRegistry.class).getExtension("zk");
    }

    /**
     * 添加一个服务
     *
     * @param rpcServiceConfig RPC服务配置
     */
    @Override
    public void addService(RpcServiceConfig rpcServiceConfig) {
        String rpcServiceName = rpcServiceConfig.getRpcServiceName();

        // 如果已注册服务中存在当前服务，就直接返回
        if (registeredService.contains(rpcServiceName)) {
            return;
        }

        // 往 Set 中添加服务名
        registeredService.add(rpcServiceName);

        // 往 Map 中存放 key 为服务名，value 为服务
        serviceMap.put(rpcServiceName, rpcServiceConfig.getService());
        log.info("add service: {} and interfaces: {}", rpcServiceName, rpcServiceConfig.getService().getClass().getInterfaces());
    }

    /**
     * 根据 RPC服务名称 获取服务
     *
     * @param rpcServiceName RPC服务名称
     * @return 服务
     */
    @Override
    public Object getService(String rpcServiceName) {
        Object service = serviceMap.get(rpcServiceName);
        if (service == null) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_FOUND);
        }
        return service;
    }

    /**
     * 发布服务
     *
     * @param rpcServiceConfig RPC服务配置
     */
    @Override
    public void publishService(RpcServiceConfig rpcServiceConfig) {
        try {
            // 获取当前地址
            String host = InetAddress.getLocalHost().getHostAddress();

            // 添加服务
            this.addService(rpcServiceConfig);

            // 注册服务
            serviceRegistry.registerService(rpcServiceConfig.getRpcServiceName(), new InetSocketAddress(host, NettyRpcServer.PORT));
        } catch (UnknownHostException e) {
            log.error("occur exception when getHostAddress", e);
        }
    }
}
