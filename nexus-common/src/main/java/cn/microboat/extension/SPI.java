package cn.microboat.extension;

import java.lang.annotation.*;

/**
 * 作用在运行时
 * 注解加在 类、接口、注解、枚举
 *
 * @author zhouwei
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SPI {
}
