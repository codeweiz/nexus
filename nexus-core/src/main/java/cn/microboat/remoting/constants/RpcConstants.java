package cn.microboat.remoting.constants;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author zhouwei
 */
public class RpcConstants {

    /**
     * 魔数，占四个字节，用来验证接收的数据符不符合规范
     */
    public static final byte[] MAGIC_NUMBER = {(byte) 'g', (byte) 'r', (byte) 'p', (byte) 'c'};

    /**
     * 默认字符集
     */
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * 版本信息
     */
    public static final byte VERSION = 1;

    /**
     * 总长度
     */
    public static final byte TOTAL_LENGTH = 16;

    /**
     * 请求类型
     */
    public static final byte REQUEST_TYPE = 1;

    /**
     * 响应类型
     */
    public static final byte RESPONSE_TYPE = 2;

    /**
     * 请求心跳类型
     */
    public static final byte HEARTBEAT_REQUEST_TYPE = 3;

    /**
     * 响应心跳类型
     */
    public static final byte HEARTBEAT_RESPONSE_TYPE = 4;

    /**
     * 头长度
     */
    public static final int HEAD_LENGTH = 16;

    /**
     * PING
     */
    public static final String PING = "ping";

    /**
     * PONG
     */
    public static final String PONG = "pong";

    /**
     * 最大帧长度
     */
    public static final int MAX_FRAME_LENGTH = 8 * 1024 * 1024;
}