package cn.microboat.extension;

import cn.microboat.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author zhouwei
 */
@Slf4j
public class ExtensionLoader<T> {

    private static final String SERVICE_DIRECTORY = "META-INF/extensions/";

    private static final Map<Class<?>, ExtensionLoader<?>> EXTENSION_LOADERS = new ConcurrentHashMap<>();

    private static final Map<Class<?>, Object> EXTENSION_INSTANCES = new ConcurrentHashMap<>();

    private final Class<?> type;

    private final Map<String, Holder<Object>> cachedInstances = new ConcurrentHashMap<>();

    private final Holder<Map<String, Class<?>>> cachedClasses = new Holder<>();

    public ExtensionLoader(Class<?> type) {
        this.type = type;
    }

    /**
     * 获取扩展加载器
     *
     * @param type 类型
     * @return ExtensionLoader
     */
    public static <S> ExtensionLoader<S> getExtensionLoader(Class<S> type) {
        // 如果参数为空，抛出非法参数异常
        if (type == null) {
            throw new IllegalArgumentException("Extension type should not be null.");
        }
        // 如果参数不是接口，抛出非法参数异常
        if (!type.isInterface()) {
            throw new IllegalArgumentException("Extension type must be an interface.");
        }
        // 如果参数没有被 @SPI 注解修饰，抛出非法参数异常
        if (type.getAnnotation(SPI.class) == null) {
            throw new IllegalArgumentException("Extension type must be annotated by @SPI");
        }
        // 第一次从 EXTENSION_LOADERS 中获取
        ExtensionLoader<S> extensionLoader = (ExtensionLoader<S>) EXTENSION_LOADERS.get(type);
        // 如果获取对象为空，就 new 一个 ExtensionLoader，即使 new ExtensionLoader<S>(type) 的返回为空，也一样插入
        if (extensionLoader == null) {
            EXTENSION_LOADERS.putIfAbsent(type, new ExtensionLoader<S>(type));
            extensionLoader = (ExtensionLoader<S>) EXTENSION_LOADERS.get(type);
        }
        return extensionLoader;
    }

    /**
     * 根据名称获取扩展
     *
     * @param name 名称
     * @return T
     */
    public T getExtension(String name) {
        // 如果参数为空或者空串，抛出非法参数异常
        if (StringUtil.isBlank(name)) {
            throw new IllegalArgumentException("Extension name should not be null or empty.");
        }
        // 首先从缓存实例中获取对应的 Holder
        Holder<Object> holder = cachedInstances.get(name);
        // 如果获取的 holder 为空，就 new 一个 Holder
        if (holder == null) {
            cachedInstances.putIfAbsent(name, new Holder<>());
            holder = cachedInstances.get(name);
        }
        // 从 holder 中获取值
        Object instance = holder.getValue();
        // 如果 holder 中的值为空，给 holder 对象加锁，再从 holder 中获取值
        // 如果值依然为空，就根据名称创建一个实例，并保存在 holder 中
        if (instance == null) {
            synchronized (holder) {
                instance = holder.getValue();
                if (instance == null) {
                    instance = createExtension(name);
                    holder.setValue(instance);
                }
            }
        }
        return (T) instance;
    }

    /**
     * 创建扩展
     *
     * @param name 名称
     * @return T
     */
    private T createExtension(String name) {
        // load all extension classes of type T from file and get specific one by name
        Class<?> clazz = getExtensionClasses().get(name);
        if (clazz == null) {
            throw new RuntimeException("No such extension of name " + name);
        }
        T instance = (T) EXTENSION_INSTANCES.get(clazz);
        if (instance == null) {
            try {
                EXTENSION_INSTANCES.putIfAbsent(clazz, clazz.newInstance());
                instance = (T) EXTENSION_INSTANCES.get(clazz);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return instance;
    }

    private Map<String, Class<?>> getExtensionClasses() {
        // get the loaded extension class from the cache
        Map<String, Class<?>> classes = cachedClasses.getValue();
        // double check
        if (classes == null) {
            synchronized (cachedClasses) {
                classes = cachedClasses.getValue();
                if (classes == null) {
                    classes = new HashMap<>();
                    // load all extensions from our extensions directory
                    loadDirectory(classes);
                    cachedClasses.setValue(classes);
                }
            }
        }
        return classes;
    }

    private void loadDirectory(Map<String, Class<?>> extensionClasses) {
        String fileName = ExtensionLoader.SERVICE_DIRECTORY + type.getName();
        try {
            Enumeration<URL> urls;
            ClassLoader classLoader = ExtensionLoader.class.getClassLoader();
            urls = classLoader.getResources(fileName);
            if (urls != null) {
                while (urls.hasMoreElements()) {
                    URL resourceUrl = urls.nextElement();
                    loadResource(extensionClasses, classLoader, resourceUrl);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void loadResource(Map<String, Class<?>> extensionClasses, ClassLoader classLoader, URL resourceUrl) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resourceUrl.openStream(), UTF_8))) {
            String line;
            // read every line
            while ((line = reader.readLine()) != null) {
                // get index of comment
                final int ci = line.indexOf('#');
                if (ci >= 0) {
                    // string after # is comment so we ignore it
                    line = line.substring(0, ci);
                }
                line = line.trim();
                if (line.length() > 0) {
                    try {
                        final int ei = line.indexOf('=');
                        String name = line.substring(0, ei).trim();
                        String clazzName = line.substring(ei + 1).trim();
                        // our SPI use key-value pair so both of them must not be empty
                        if (name.length() > 0 && clazzName.length() > 0) {
                            Class<?> clazz = classLoader.loadClass(clazzName);
                            extensionClasses.put(name, clazz);
                        }
                    } catch (ClassNotFoundException e) {
                        log.error(e.getMessage());
                    }
                }

            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
