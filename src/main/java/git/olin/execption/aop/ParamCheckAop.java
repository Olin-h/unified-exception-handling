package git.olin.execption.aop;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import git.olin.execption.annotation.ParamCheck;
import git.olin.execption.exception.ParamIsNullException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 方法入参校验-aop
 * Aop相关参数介绍详情参考：https://blog.csdn.net/mu_wind/article/details/102758005
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2021-02-28
 */
@Component
@Aspect
@Slf4j
public class ParamCheckAop {
    /**
     * 定义有一个切入点，controller包下及其子包下的所有类的所有方法
     * 第一个【*】：任何返回值
     * 第二个【*】：任何类
     * 第三个【*】：任何方法
     * 第一个【..】：所有子包
     * 第二个【..】：所有参数
     * Pointcut注解的使用请参考：https://www.jianshu.com/p/3c73065ecbdf
     */
    @Pointcut("execution(public * git.olin.execption.controller..*.*(..))")
    public void checkParam() {
    }

    /**
     * 前置增强方法，主要做环绕增强功能前的预处理
     */
    @Before("checkParam()")
    public void doBefore(JoinPoint joinPoint) {
    }

    /**
     * 环绕增强方法，主要是做检查参数是否为空判断
     */
    @Around("checkParam()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        // 方法签名
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        // 得到拦截的方法
        Method method = signature.getMethod();
        // 获取方法参数注解，返回二维数组是因为某些参数可能存在多个注解
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        if (ArrayUtil.isEmpty(parameterAnnotations)) {
            return pjp.proceed();
        }
        // 获取方法参数名
        String[] paramNames = signature.getParameterNames();
        // 获取参数值
        Object[] paramArgs = pjp.getArgs();
        // 获取方法参数类型
        Class<?>[] parameterTypes = method.getParameterTypes();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (int j = 0; j < parameterAnnotations[i].length; j++) {
                // 如果该参数前面的注解是ParamCheck的实例，并且notNull()=true,则进行非空校验
                if (parameterAnnotations[i][j] != null && parameterAnnotations[i][j] instanceof ParamCheck && ((ParamCheck) parameterAnnotations[i][j]).notNull()) {
                    // 校验方法参数是否为空
                    this.paramIsNull(paramNames[i], paramArgs[i], parameterTypes[i] == null ? null : parameterTypes[i].getName());
                    break;
                }
            }
        }
        return pjp.proceed();
    }

    /**
     * 后置增强方法，在切入点return内容之后切入内容（可以用来对处理返回值做一些加工处理）
     *
     * @param joinPoint ：切入点
     */
    @AfterReturning("checkParam()")
    public void doAfterReturning(JoinPoint joinPoint) {
    }

    /**
     * 参数非空校验，如果参数为空，则抛出ParamIsNullException异常
     *
     * @param paramName ：参数名称
     * @param value     ：参数值
     * @param paramType ：参数类型
     */
    private void paramIsNull(String paramName, Object value, String paramType) {
        if (ObjectUtil.isEmpty(value)) {
            throw new ParamIsNullException(paramName, paramType);
        }
    }
}
