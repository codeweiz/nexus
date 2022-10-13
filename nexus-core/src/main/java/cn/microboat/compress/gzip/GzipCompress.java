package cn.microboat.compress.gzip;

import cn.microboat.compress.Compress;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * GZIP实现类
 *
 * @author zhouwei
 */
public class GzipCompress implements Compress {

    private static final int BUFFER_SIZE = 1024 * 4;

    @Override
    public byte[] compress(byte[] bytes) {
        // 如果要压缩的数组为空，抛出空指针异常
        if (bytes == null) {
            throw new NullPointerException("bytes is null");
        }

        // 初始化 ByteArrayOutputStream、GZIPOutputStream
        try (
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                GZIPOutputStream gzip = new GZIPOutputStream(out)
        ) {
            gzip.write(bytes);
            gzip.flush();
            gzip.finish();
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("gzip compress error", e);
        }
    }

    @Override
    public byte[] decompress(byte[] bytes) {
        // 如果要解压缩的数组为空，抛出空指针异常
        if (bytes == null) {
            throw new NullPointerException("bytes is null");
        }

        // 初始化 ByteArrayOutputStream、ByteArrayInputStream、GZIPInputStream
        try (
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                GZIPInputStream gunzip = new GZIPInputStream(new ByteArrayInputStream(bytes))
        ) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int n;
            while ((n = gunzip.read(buffer)) > -1) {
                out.write(buffer, 0, n);
            }
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("gzip decompress error", e);
        }
    }
}
