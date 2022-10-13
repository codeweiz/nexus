package cn.microboat.provider;

import cn.microboat.config.RpcServiceConfig;

/**
 * 服务提供者
 *
 * @author zhouwei
 */
public interface ServiceProvider {

    /**
     * 添加一个服务
     *
     * @param rpcServiceConfig RPC服务配置
     */
    void addService(RpcServiceConfig rpcServiceConfig);

    /**
     * 根据 RPC服务名称 获取服务
     *
     * @param rpcServiceName RPC服务名称
     * @return 服务
     */
    Object getService(String rpcServiceName);

    /**
     * 发布服务
     *
     * @param rpcServiceConfig RPC服务配置
     */
    void publishService(RpcServiceConfig rpcServiceConfig);
}
