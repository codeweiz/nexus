package cn.microboat.provider.impl;

import cn.microboat.config.RpcServiceConfig;
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
 * @author zhouwei
 */
@Slf4j
public class ZkServiceProviderImpl implements ServiceProvider {

    private final Map<String, Object> serviceMap;

    private final Set<String> registeredService;

    private final ServiceRegistry serviceRegistry;

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
        if (registeredService.contains(rpcServiceName)) {
            return;
        }
        registeredService.add(rpcServiceName);
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
            String host = InetAddress.getLocalHost().getHostAddress();
            this.addService(rpcServiceConfig);
            serviceRegistry.registerService(rpcServiceConfig.getRpcServiceName(), new InetSocketAddress(host, NettyRpcServer.PORT));
        } catch (UnknownHostException e) {
            log.error("occur exception when getHostAddress", e);
        }

    }
}
