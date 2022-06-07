package cn.microboat.remoting.transport.socket;

import cn.microboat.config.CustomShutdownHook;
import cn.microboat.config.RpcServiceConfig;
import cn.microboat.factory.SingletonFactory;
import cn.microboat.provider.ServiceProvider;
import cn.microboat.provider.impl.ZkServiceProviderImpl;
import cn.microboat.utils.ThreadPoolFactoryUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import static cn.microboat.remoting.transport.netty.server.NettyRpcServer.PORT;

/**
 * @author zhouwei
 */
@Slf4j
public class SocketRpcServer {

    private final ExecutorService threadPool;

    private final ServiceProvider serviceProvider;


    public SocketRpcServer() {
        this.threadPool = ThreadPoolFactoryUtil.createCustomThreadPoolIfAbsent("socket-server-rpc-pool");
        this.serviceProvider = SingletonFactory.getInstance(ZkServiceProviderImpl.class);
    }

    public void registerService(RpcServiceConfig rpcServiceConfig) {
        serviceProvider.publishService(rpcServiceConfig);
    }

    public void start() {
        try (ServerSocket server = new ServerSocket()) {
            String host = InetAddress.getLocalHost().getHostAddress();
            server.bind(new InetSocketAddress(host, PORT));
            CustomShutdownHook.getCustomShutdownHook().clearAll();
            Socket socket;
            while ((socket = server.accept()) != null) {
                log.info("client connected [{}]", socket.getInetAddress());
                threadPool.execute(new SocketRpcRequestHandlerRunnable(socket));
            }
            threadPool.shutdown();
        } catch (IOException e) {
            log.error("occur IOException:", e);
        }
    }
}
