package cn.microboat.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 自定义线程池配置
 *
 * @author zhouwei
 */
@Setter
@Getter
public class CustomThreadPoolConfig {
    /**
     * 线程池默认核心池大小
     */
    private static final int DEFAULT_CORE_POOL_SIZE = 10;

    /**
     * 线程池默认最大池大小
     */
    private static final int DEFAULT_MAXIMUM_POOL_SIZE_SIZE = 100;

    /**
     * 线程池默认保持存活时间
     */
    private static final int DEFAULT_KEEP_ALIVE_TIME = 1;

    /**
     * 线程池默认时间单位
     */
    private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.MINUTES;

    /**
     * 线程池默认阻塞队列容量
     */
    private static final int DEFAULT_BLOCKING_QUEUE_CAPACITY = 100;

    /**
     * 线程池阻塞队列容量
     */
    private static final int BLOCKING_QUEUE_CAPACITY = 100;

    /**
     * 可配置参数
     */
    private int corePoolSize = DEFAULT_CORE_POOL_SIZE;
    private int maximumPoolSize = DEFAULT_MAXIMUM_POOL_SIZE_SIZE;
    private long keepAliveTime = DEFAULT_KEEP_ALIVE_TIME;
    private TimeUnit unit = DEFAULT_TIME_UNIT;

    /**
     * 使用有界队列
     */
    private BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(BLOCKING_QUEUE_CAPACITY);
}
