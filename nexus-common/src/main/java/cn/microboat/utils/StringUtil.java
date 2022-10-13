package cn.microboat.utils;

/**
 * 字符串工具类
 *
 * @author zhouwei
 */
public class StringUtil {

    /**
     * 判空
     *
     * @param s String
     * @return boolean
     */
    public static boolean isBlank(String s) {
        // 如果字符串为空 获取 字符串长度为0，返回true
        if (s == null || s.length() == 0) {
            return true;
        }
        // 在字符串长度大于0的情况下，每一个字符都不是空白，返回 false
        for (int i = 0; i < s.length(); ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        // 默认返回true
        return true;
    }
}
