package git.olin.execption.service;

import git.olin.execption.entity.CommonException;

/**
 * 异常处理服务接口
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2021-02-18
 */
public interface IExceptionHandlingService {
    /**
     * 创建一个CommonException
     *
     * @return {CommonException}
     */
    CommonException build();
}
