package cn.microboat.utils;

/**
 * 运行时工具类
 *
 * @author zhouwei
 */
public class RuntimeUtil {
    /**
     * 获取CPU的最大有效核心数
     *
     * @return cpu的核心数
     */
    public static int cpus() {
        return Runtime.getRuntime().availableProcessors();
    }
}