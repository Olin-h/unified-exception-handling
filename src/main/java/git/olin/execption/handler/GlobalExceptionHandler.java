package git.olin.execption.handler;

import cn.hutool.http.HttpStatus;
import git.olin.execption.common.R;
import git.olin.execption.exception.ParamIsNullException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常拦截处理
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2021-03-28
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 自定义异常的拦截处理
     */
    @ExceptionHandler(value = {MissingServletRequestParameterException.class, ParamIsNullException.class})
    public R<String> customErrorHandler(Exception e) {
        log.error("请求发生异常，异常为：", e);
        return R.build(HttpStatus.HTTP_INTERNAL_ERROR, e.getMessage(), null);
    }

    /**
     * 所有的异常的拦截处理
     */
    @ExceptionHandler(value = Exception.class)
    public R<String> allErrorHandler(HttpServletRequest request, Exception e) {
        log.error("[{}]请求发生异常，异常为：", request.getRequestURI(), e);
        return R.fail();
    }
}
