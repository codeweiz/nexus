package cn.microboat.remoting.dto;

import cn.microboat.enums.RpcResponseCodeEnum;
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
public class RpcResponse<T> implements Serializable {

    /**
     * 序列号 id
     */
    private static final long serialVersionUID = 715745410605631233L;

    /**
     * 请求 id
     */
    private String requestId;

    /**
     * 响应码 code
     */
    private Integer code;

    /**
     * 响应消息 message
     */
    private String message;

    /**
     * 响应体 body
     */
    private T data;

    /**
     * 成功
     *
     * @param data      数据
     * @param requestId 请求 id
     * @return RpcResponse
     */
    public static <T> RpcResponse<T> success(T data, String requestId) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setCode(RpcResponseCodeEnum.SUCCESS.getCode());
        response.setMessage(RpcResponseCodeEnum.SUCCESS.getMessage());
        response.setRequestId(requestId);
        if (null != data) {
            response.setData(data);
        }
        return response;
    }

    /**
     * 失败
     *
     * @param rpcResponseCodeEnum RPC 响应码枚举
     * @return RpcResponse
     */
    public static <T> RpcResponse<T> fail(RpcResponseCodeEnum rpcResponseCodeEnum) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setCode(rpcResponseCodeEnum.getCode());
        response.setMessage(rpcResponseCodeEnum.getMessage());
        return response;
    }
}
