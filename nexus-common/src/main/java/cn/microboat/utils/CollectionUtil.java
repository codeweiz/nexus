package cn.microboat.utils;

import java.util.Collection;

/**
 * @author zhouwei
 */
public class CollectionUtil {

    public static boolean isEmpty(Collection<?> c) {
        return c == null || c.isEmpty();
    }

}