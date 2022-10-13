package cn.microboat.loadbalance;

import cn.microboat.remoting.dto.RpcRequest;
import cn.microboat.utils.CollectionUtil;

import java.util.List;

/**
 * 负载均衡抽象类
 *
 * @author zhouwei
 */
public abstract class AbstractLoadBalance implements LoadBalance {
    @Override
    public String selectServiceAddress(List<String> serviceUrlList, RpcRequest rpcRequest) {
        // 如果 serviceUrlList 服务URL列表为空，直接返回 null
        if (CollectionUtil.isEmpty(serviceUrlList)) {
            return null;
        }
        // 如果列表里只有一个值，返回这个值
        if (serviceUrlList.size() == 1) {
            return serviceUrlList.get(0);
        }
        return doSelect(serviceUrlList, rpcRequest);
    }

    /**
     * 做选择
     *
     * @param serviceAddresses 服务地址
     * @param rpcRequest       RPC 请求
     * @return String
     */
    protected abstract String doSelect(List<String> serviceAddresses, RpcRequest rpcRequest);
}
