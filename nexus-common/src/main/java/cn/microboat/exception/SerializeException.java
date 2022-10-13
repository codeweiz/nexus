package cn.microboat.exception;

/**
 * 封装异常类 SerializeException
 *
 * @author zhouwei
 */
public class SerializeException extends RuntimeException {
    public SerializeException(String message) {
        super(message);
    }
}