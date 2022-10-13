package cn.microboat.utils;

import java.util.Collection;

/**
 * 集合工具类
 *
 * @author zhouwei
 */
public class CollectionUtil {

    /**
     * 判空，如果等于 null 或者 .isEmpty() 为 true 就为空
     *
     * @param c 集合
     * @return boolean
     */
    public static boolean isEmpty(Collection<?> c) {
        return c == null || c.isEmpty();
    }

}