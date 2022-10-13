package cn.microboat.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 压缩类型枚举
 *
 * @author zhouwei
 */
@AllArgsConstructor
@Getter
public enum CompressTypeEnum {

    /**
     * GZIP
     */
    GZIP((byte) 0x01, "gzip");

    /**
     * code
     */
    private final byte code;

    /**
     * name
     */
    private final String name;

    /**
     * 根据 code 获取 name
     *
     * @param code code
     * @return String
     */
    public static String getName(byte code) {
        for (CompressTypeEnum c : CompressTypeEnum.values()) {
            if (c.getCode() == code) {
                return c.name;
            }
        }
        return null;
    }

}