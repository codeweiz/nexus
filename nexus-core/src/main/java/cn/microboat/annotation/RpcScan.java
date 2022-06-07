package cn.microboat.annotation;

import cn.microboat.spring.CustomScannerRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author zhouwei
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Import(CustomScannerRegister.class)
public @interface RpcScan {

    String[] basePackage();
}
