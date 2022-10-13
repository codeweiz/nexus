package cn.microboat.loadbalance;

import cn.microboat.extension.SPI;
import cn.microboat.remoting.dto.RpcRequest;

import java.util.List;

/**
 * 负载均衡接口
 *
 * @author zhouwei
 */
@SPI
public interface LoadBalance {

    /**
     * 选择服务地址
     *
     * @param rpcRequest     RPC 请求
     * @param serviceUrlList 服务列表
     * @return String
     */
    String selectServiceAddress(List<String> serviceUrlList, RpcRequest rpcRequest);
}
