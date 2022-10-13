package cn.microboat.loadbalance.loadbalancer;

import cn.microboat.loadbalance.AbstractLoadBalance;
import cn.microboat.remoting.dto.RpcRequest;

import java.util.List;
import java.util.Random;

/**
 * 随机负载均衡
 *
 * @author zhouwei
 */
public class RandomLoadBalance extends AbstractLoadBalance {
    @Override
    protected String doSelect(List<String> serviceAddresses, RpcRequest rpcRequest) {
        // 通过 Random 实例的 nextInt 方法，传入 serviceAddresses.size() 边界，获取随机数
        Random random = new Random();
        return serviceAddresses.get(random.nextInt(serviceAddresses.size()));
    }
}
