package git.olin.execption.entity;

import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;

/**
 * 模拟关闭
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2021-03-28
 */
@Slf4j
public class SimulateClose implements Closeable {

    public void beforeClose() {
        log.info(" >>>> 发送数据前的操作！");
    }

    @Override
    public void close() throws IOException {
        log.info(" >>>> 正在关闭发送操作！");
    }
}
