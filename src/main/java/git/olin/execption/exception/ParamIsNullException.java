package git.olin.execption.exception;

import lombok.Getter;

/**
 * 自定义异常——判断参数是否为空的异常
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2021-02-28
 */
@Getter
public class ParamIsNullException extends RuntimeException {
    private final String paramName;
    private final String paramType;

    public ParamIsNullException(String paramName, String paramType) {
        super("");
        this.paramName = paramName;
        this.paramType = paramType;
    }

    @Override
    public String getMessage() {
        return "参数类型[ " + this.paramType + " ]的参数[ " + this.paramName + " ]不能为空！";
    }
}
