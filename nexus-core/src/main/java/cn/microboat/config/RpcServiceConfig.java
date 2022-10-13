package cn.microboat.config;

import lombok.*;

/**
 * RPC 服务配置
 *
 * @author zhouwei
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RpcServiceConfig {

    /**
     * 版本号
     */
    private String version = "";

    /**
     * 群组
     */
    private String group = "";

    /**
     * 服务
     */
    private Object service;

    /**
     * 获取RPC服务名
     */
    public String getRpcServiceName() {
        return this.getServiceName() + this.getGroup() + this.getVersion();
    }

    /**
     * 获取服务名
     */
    public String getServiceName() {
        return this.service.getClass().getInterfaces()[0].getCanonicalName();
    }
}
