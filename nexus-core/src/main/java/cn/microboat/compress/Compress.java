package cn.microboat.compress;

import cn.microboat.extension.SPI;

/**
 * @author zhouwei
 */
@SPI
public interface Compress {

    byte[] compress(byte[] bytes);

    byte[] decompress(byte[] bytes);

}
