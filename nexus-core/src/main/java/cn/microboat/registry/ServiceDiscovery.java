package cn.microboat.registry;

import cn.microboat.extension.SPI;
import cn.microboat.remoting.dto.RpcRequest;

import java.net.InetSocketAddress;

/**
 * 服务发现
 *
 * @author zhouwei
 */
@SPI
public interface ServiceDiscovery {
    /**
     * lookup service by rpcServiceName
     * 根据 RPC 服务名称查找 InetSocketAddress
     *
     * @param rpcRequest rpc service pojo
     * @return service address
     */
    InetSocketAddress lookupService(RpcRequest rpcRequest);
}