package cn.microboat.loadbalance;

import cn.microboat.remoting.dto.RpcRequest;
import cn.microboat.utils.CollectionUtil;

import java.util.List;

/**
 * @author zhouwei
 */
public abstract class AbstractLoadBalance implements LoadBalance {
    @Override
    public String selectServiceAddress(List<String> serviceUrlList, RpcRequest rpcRequest) {
        if (CollectionUtil.isEmpty(serviceUrlList)) {
            return null;
        }
        if (serviceUrlList.size() == 1) {
            return serviceUrlList.get(0);
        }
        return doSelect(serviceUrlList, rpcRequest);
    }

    protected abstract String doSelect(List<String> serviceAddresses, RpcRequest rpcRequest);
}
