package git.olin.execption.service.impl;

import git.olin.execption.entity.CommonException;
import git.olin.execption.service.IExceptionHandlingService;
import org.springframework.stereotype.Service;

/**
 * 异常处理服务接口实现类
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2021-02-18
 */
@Service
public class ExceptionHandlingServiceImpl implements IExceptionHandlingService {

    /**
     * 创建一个CommonException
     *
     * @return {CommonException}
     */
    @Override
    public CommonException build() {
        /*return new CommonException();*/
        return null;
    }
}
