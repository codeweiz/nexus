package cn.microboat.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * 配置文件工具类
 *
 * @author zhouwei
 */
@Slf4j
public final class PropertiesFileUtil {
    private PropertiesFileUtil() {
    }

    /**
     * 读取配置文件
     *
     * @param fileName 文件名
     * @return Properties
     */
    public static Properties readPropertiesFile(String fileName) {
        // 从线程中获取当前线程，再获取上下文类加载器，再获取资源，用 URL 接收
        URL url = Thread.currentThread().getContextClassLoader().getResource("");
        // RPC 配置文件路径
        String rpcConfigPath = "";
        // 如果资源 URL 不为空，rpcConfigPath 就是路径加文件名
        if (url != null) {
            rpcConfigPath = url.getPath() + fileName;
        }
        // 初始化 Properties 类
        Properties properties = null;
        // 根据路径字符串，获取 Path；根据 Path 获取输入流；根据输入流和字符集编码，获取输入流阅读器
        // 通过 Properties 实例的 load 方法把输入流阅读器加载
        try (InputStreamReader inputStreamReader = new InputStreamReader(Files.newInputStream(Paths.get(rpcConfigPath)), StandardCharsets.UTF_8)) {
            properties = new Properties();
            properties.load(inputStreamReader);
        } catch (IOException e) {
            log.error("occur exception when read properties file [{}]", fileName);
        }
        return properties;
    }
}
