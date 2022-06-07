package cn.microboat.loadbalance;

import cn.microboat.extension.SPI;
import cn.microboat.remoting.dto.RpcRequest;

import java.util.List;

/**
 * @author zhouwei
 */
@SPI
public interface LoadBalance {

    String selectServiceAddress(List<String> serviceUrlList, RpcRequest rpcRequest);
}
