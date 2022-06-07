package cn.microboat.annotation;

import java.lang.annotation.*;

/**
 * @author zhouwei
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Inherited
public @interface RpcReference {

    String version() default "";

    String group() default "";
}
