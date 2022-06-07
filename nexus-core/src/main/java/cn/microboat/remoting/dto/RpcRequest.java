package cn.microboat.remoting.dto;

import lombok.*;

import java.io.Serializable;

/**
 * @author zhouwei
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RpcRequest implements Serializable {

    /**
     * 序列化 id
     */
    private static final long serialVersionUID = 1905122041950251207L;

    /**
     * 请求 id
     */
    private String requestId;

    /**
     * 接口名称
     */
    private String interfaceName;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 参数数组
     */
    private Object[] parameters;

    /**
     * 参数类型
     */
    private Class<?>[] paramTypes;

    /**
     * 版本号
     */
    private String version;

    /**
     * 群组
     */
    private String group;

    /**
     * 获取 RPC 服务名称
     *
     * @return String
     */
    public String getRpcServiceName() {
        return this.getInterfaceName() + this.getGroup() + this.getVersion();
    }
}
