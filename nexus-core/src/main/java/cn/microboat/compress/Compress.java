package cn.microboat.compress;

import cn.microboat.extension.SPI;

/**
 * 压缩接口
 *
 * @author zhouwei
 */
@SPI
public interface Compress {

    /**
     * 压缩
     *
     * @param bytes 数组
     * @return byte[]
     */
    byte[] compress(byte[] bytes);

    /**
     * 解压缩
     *
     * @param bytes 数组
     * @return byte[]
     */
    byte[] decompress(byte[] bytes);

}
