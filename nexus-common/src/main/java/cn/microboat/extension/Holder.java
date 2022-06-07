package cn.microboat.extension;

import lombok.Data;

/**
 * @author zhouwei
 */
@Data
public class Holder<T> {

    private volatile T value;
}
