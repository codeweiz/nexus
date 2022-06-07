package cn.microboat.remoting.transport;

import cn.microboat.extension.SPI;
import cn.microboat.remoting.dto.RpcRequest;

/**
 * @author zhouwei
 */
@SPI
public interface RpcRequestTransport {

    /**
     * 发送 RPC 请求
     *
     * @param rpcRequest RPC请求
     * @return Object
     */
    Object sendRpcRequest(RpcRequest rpcRequest);

}
