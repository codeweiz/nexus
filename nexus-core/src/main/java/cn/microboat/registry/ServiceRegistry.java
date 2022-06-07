package cn.microboat.registry;

import cn.microboat.extension.SPI;

import java.net.InetSocketAddress;

/**
 * @author zhouwei
 */
@SPI
public interface ServiceRegistry {

    /**
     * 注册服务
     *
     * @param rpcServiceName RPC 服务名称
     * @param inetSocketAddress 网络套接字地址
     * */
    void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress);
}
