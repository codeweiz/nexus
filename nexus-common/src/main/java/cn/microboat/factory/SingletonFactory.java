package cn.microboat.factory;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 单例工厂类
 *
 * @author zhouwei
 */
public final class SingletonFactory {

    /**
     * ConcurrentHashMap 作为 对象 map
     */
    private static final Map<String, Object> OBJECT_MAP = new ConcurrentHashMap<>();

    private SingletonFactory() {
    }

    /**
     * 获取实例方法
     *
     * @param c 类
     * @return T
     */
    public static <T> T getInstance(Class<T> c) {
        // 如果传入的参数为空，直接抛出非法参数异常
        if (c == null) {
            throw new IllegalArgumentException();
        }

        // 获取类的字符串，以此作为 map 的 key
        String key = c.toString();

        // 如果 OBJECT_MAP 对象 map 中含有这个 key，那就从 OBJECT_MAP 中获取对应的值，并进行类型转换为 T
        if (OBJECT_MAP.containsKey(key)) {
            return c.cast(OBJECT_MAP.get(key));
        } else {
            // 如果 OBJECT_MAP 对象 map 中不含有这个 key，使用构造器获取一个实例
            return c.cast(OBJECT_MAP.computeIfAbsent(key, k -> {
                try {
                    return c.getDeclaredConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException
                         | InvocationTargetException | NoSuchMethodException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }));
        }
    }
}
