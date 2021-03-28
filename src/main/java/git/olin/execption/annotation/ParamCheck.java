package git.olin.execption.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 方法参数检测，主要作用于方法参数上
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2021-02-28
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamCheck {
    boolean notNull() default true;
}
