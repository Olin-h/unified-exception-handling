package git.olin.execption.common;

import cn.hutool.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一返回结果
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2021-03-28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class R<T> {
    /**
     * 响应码
     */
    private Integer resCode;
    /**
     * 响应信息
     */
    private String resMsg;
    /**
     * 返回的泛型数据
     */
    private T data;

    /**
     * 根据消息码等生成接口返回对象
     *
     * @param code ：结果返回码
     * @param msg  ：结果返回消息
     * @param data ：数据对象
     * @param <T>  : 泛型实体
     * @return {<T> R<T>}
     */
    public static <T> R<T> build(int code, String msg, T data) {
        return new R<>(code, msg, data);
    }

    /**
     * 请求失败返回结果
     *
     * @param <T> ：泛型实体
     * @return {<T> R<T>}
     */
    public static <T> R<T> fail() {
        return build(HttpStatus.HTTP_INTERNAL_ERROR, "服务异常", null);
    }

    /**
     * 请求成功返回结果
     *
     * @param <T> ：泛型实体
     * @return {<T> R<T>}
     */
    public static <T> R<T> success() {
        return build(HttpStatus.HTTP_OK, "请求成功", null);
    }
}
