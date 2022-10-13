package cn.microboat.extension;

import lombok.Data;

/**
 * 包装类，提供通用泛型封装
 * volatile 保证可见性、禁止指令重排
 *
 * @author zhouwei
 */
@Data
public class Holder<T> {

    private volatile T value;
}
