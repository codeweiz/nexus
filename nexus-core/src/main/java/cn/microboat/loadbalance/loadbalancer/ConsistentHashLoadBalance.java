package cn.microboat.loadbalance.loadbalancer;

import cn.microboat.loadbalance.AbstractLoadBalance;
import cn.microboat.remoting.dto.RpcRequest;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 一致性哈希负载均衡
 *
 * @author zhouwei
 */
public class ConsistentHashLoadBalance extends AbstractLoadBalance {

    /**
     * 使用 ConcurrentHashMap 作为选择器 selector 容器
     * key 为 String
     * value 为 ConsistentHashSelector 一致性哈希选择器
     */
    private final ConcurrentHashMap<String, ConsistentHashSelector> selectors = new ConcurrentHashMap<>();

    @Override
    protected String doSelect(List<String> serviceAddresses, RpcRequest rpcRequest) {
        int identityHashCode = System.identityHashCode(serviceAddresses);
        // build rpc service name by rpcRequest
        String rpcServiceName = rpcRequest.getRpcServiceName();
        ConsistentHashSelector selector = selectors.get(rpcServiceName);
        // check for updates
        if (selector == null || selector.identityHashCode != identityHashCode) {
            selectors.put(rpcServiceName, new ConsistentHashSelector(serviceAddresses, 160, identityHashCode));
            selector = selectors.get(rpcServiceName);
        }
        return selector.select(rpcServiceName + Arrays.stream(rpcRequest.getParameters()));
    }

    /**
     * 自定义一致性哈希选择器
     */
    static class ConsistentHashSelector {

        /**
         * 虚拟调用者
         * 使用 TreeMap 作为容器
         * key 为 Long 类型
         * value 为 String 类型
         */
        private final TreeMap<Long, String> virtualInvokers;

        /**
         * 身份哈希码，系统生成的，全局唯一
         */
        private final int identityHashCode;

        /**
         * 有参构造
         *
         * @param invokers         调用者
         * @param replicaNumber    副本数字
         * @param identityHashCode 身份哈希码
         */
        ConsistentHashSelector(List<String> invokers, int replicaNumber, int identityHashCode) {
            this.virtualInvokers = new TreeMap<>();
            this.identityHashCode = identityHashCode;

            for (String invoker : invokers) {
                for (int i = 0; i < replicaNumber / 4; i++) {
                    byte[] digest = md5(invoker + i);
                    for (int h = 0; h < 4; h++) {
                        long m = hash(digest, h);
                        virtualInvokers.put(m, invoker);
                    }
                }
            }
        }


        /**
         * 信息摘要
         *
         * @param key 键
         * @return byte[]
         */
        static byte[] md5(String key) {
            MessageDigest md;
            try {
                // 获取算法为 MD5 的实例
                md = MessageDigest.getInstance("MD5");
                // 通过 getBytes 方法，参数为 StandardCharsets.UTF_8 编码，转为字符数组
                byte[] bytes = key.getBytes(StandardCharsets.UTF_8);
                // 更新
                md.update(bytes);
            } catch (NoSuchAlgorithmException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }

            // 获取摘要
            return md.digest();
        }

        /**
         * 哈希
         *
         * @param digest 字符数组
         * @param idx int 型
         * @return long
         * */
        static long hash(byte[] digest, int idx) {
            return ((long) (digest[3 + idx * 4] & 255) << 24 | (long) (digest[2 + idx * 4] & 255) << 16 | (long) (digest[1 + idx * 4] & 255) << 8 | (long) (digest[idx * 4] & 255)) & 4294967295L;
        }

        /**
         * 对参数进行 md5 操作，获取摘要
         * 对摘要进行哈希
         *
         * */
        public String select(String rpcServiceKey) {
            byte[] digest = md5(rpcServiceKey);
            return selectForKey(hash(digest, 0));
        }

        /**
         * 根据 key 返回 值
         * */
        public String selectForKey(long hashCode) {
            // 根据 hashCode 先生成升序子Map，再通过 firstEntry 返回 Map.Entry<Long, String>
            Map.Entry<Long, String> entry = virtualInvokers.tailMap(hashCode, true).firstEntry();

            if (entry == null) {
                entry = virtualInvokers.firstEntry();
            }

            return entry.getValue();
        }
    }
}
