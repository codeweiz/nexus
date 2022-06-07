package cn.microboat.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhouwei
 */

@AllArgsConstructor
@Getter
public enum RpcConfigEnum {

    /**
     * RPC配置路径
     * */
    RPC_CONFIG_PATH("rpc.properties"),

    /**
     * Zookeeper 地址
     * */
    ZK_ADDRESS("rpc.zookeeper.address");

    private final String propertyValue;

}