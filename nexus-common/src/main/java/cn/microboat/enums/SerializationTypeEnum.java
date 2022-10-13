package cn.microboat.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 序列化类型枚举
 *
 * @author zhouwei
 */
@AllArgsConstructor
@Getter
public enum SerializationTypeEnum {

    /**
     * KYRO
     */
    KYRO((byte) 0x01, "kyro"),

    /**
     * PROTOSTUFF
     */
    PROTOSTUFF((byte) 0x02, "protostuff"),

    /**
     * HESSIAN
     */
    HESSIAN((byte) 0X03, "hessian");

    private final byte code;
    private final String name;

    /**
     * 根据 code 获取 name
     *
     * @param code code
     * @return String
     */
    public static String getName(byte code) {
        for (SerializationTypeEnum c : SerializationTypeEnum.values()) {
            if (c.getCode() == code) {
                return c.name;
            }
        }
        return null;
    }
}