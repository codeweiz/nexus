package cn.microboat.loadbalance.loadbalancer;

import cn.microboat.loadbalance.AbstractLoadBalance;
import cn.microboat.remoting.dto.RpcRequest;

import java.util.List;
import java.util.Random;

/**
 * @author zhouwei
 */
public class RandomLoadBalance extends AbstractLoadBalance {
    @Override
    protected String doSelect(List<String> serviceAddresses, RpcRequest rpcRequest) {
        Random random = new Random();
        return serviceAddresses.get(random.nextInt(serviceAddresses.size()));
    }
}
