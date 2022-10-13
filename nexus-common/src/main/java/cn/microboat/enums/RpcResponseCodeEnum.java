package cn.microboat.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * RPC 响应码枚举
 *
 * @author zhouwei
 */
@AllArgsConstructor
@Getter
@ToString
public enum RpcResponseCodeEnum {

    /**
     * 成功
     */
    SUCCESS(200, "The remote call is successful"),

    /**
     * 失败
     */
    FAIL(500, "The remote call is fail");

    private final int code;

    private final String message;
}