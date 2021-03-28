package git.olin.execption.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义异常
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2021-02-18
 */
@Data
@NoArgsConstructor
//@Builder
public class CommonException {
    private String type;
    private String name;
}
