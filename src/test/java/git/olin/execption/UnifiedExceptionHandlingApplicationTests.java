package git.olin.execption;

import cn.hutool.core.io.IoUtil;
import git.olin.execption.entity.SimulateClose;
import git.olin.execption.service.IExceptionHandlingService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.nio.charset.StandardCharsets;

@SpringBootTest
@DisplayName("统一异常处理测试类")
@Slf4j
class UnifiedExceptionHandlingApplicationTests {
    final String fileInputPathName = "src/main/resources/输入文件.txt";
    final String fileOutputPathName = "src/main/resources/输出文件.txt";

    @Autowired
    private IExceptionHandlingService exceptionHandlingService;

    @Test
    @DisplayName("测试空指针异常")
    @Disabled
    void testNullPointerException() {
        // 此处的getName方法会抛出java.lang.NullPointerException异常
        log.info("获取的异常类型为：{}", exceptionHandlingService.build().getName());
    }

    @Test
    @DisplayName("测试手动抛出异常")
    @Disabled
    void testThrowsExceptions() throws IOException {
        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(new File(fileInputPathName)));
        BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(new File(fileOutputPathName)));
        int b;
        // 将字符输入流中的内容输出出来
        final byte[] bytes = IoUtil.readBytes(bin, false);
        log.info("输入的内容：[{}]", new String(bytes, StandardCharsets.UTF_8));
        // 上述的readBytes方法的参数isCloseStream不能为true，否则会提示异常：java.io.IOException: Stream closed；
        // 导致bin.read读取时，流已经被关闭，可以查看readBytes底层方法。
        while ((b = bin.read()) != -1) {
            bout.write(b);
        }
        // 使用工具类进行关闭，使用的前提必须是需要被关闭的类必须实现Closeable接口
        IoUtil.close(bin);
        IoUtil.close(bout);
    }

    @Test
    @DisplayName("不使用工具类关闭情况下测试手动捕获异常")
    @Disabled
    void testManuallyCatchExceptionsCloseWithoutTool() {
        BufferedInputStream bin = null;
        BufferedOutputStream bout = null;
        try {
            bin = new BufferedInputStream(new FileInputStream(new File(fileInputPathName)));
            bout = new BufferedOutputStream(new FileOutputStream(new File(fileOutputPathName)));
            int b;
            // 将字符输入流中的内容输出出来
            final byte[] bytes = IoUtil.readBytes(bin, false);
            log.info("输入的内容：[{}]", new String(bytes, StandardCharsets.UTF_8));
            // 上述的readBytes方法的参数isCloseStream不能为true，否则会提示异常：java.io.IOException: Stream closed；
            // 导致bin.read读取时，流已经被关闭，可以查看readBytes底层方法。
            while ((b = bin.read()) != -1) {
                bout.write(b);
            }
        } catch (IOException e) {
            log.error("程序发生异常，异常详细信息为：", e);
        } finally {
            // 手动进行关闭
            if (bin != null) {
                try {
                    bin.close();
                } catch (IOException e) {
                    log.error("程序发生异常，异常详细信息为：", e);
                } finally {
                    if (bout != null) {
                        try {
                            bout.close();
                        } catch (IOException e) {
                            log.error("程序发生异常，异常详细信息为：", e);
                        }
                    }
                }
            }
        }
    }

    @Test
    @DisplayName("使用工具类关闭情况下测试手动捕获异常")
    @Disabled
    void testManuallyCatchExceptionsCloseWithTool() {
        BufferedInputStream bin = null;
        BufferedOutputStream bout = null;
        try {
            bin = new BufferedInputStream(new FileInputStream(new File(fileInputPathName)));
            bout = new BufferedOutputStream(new FileOutputStream(new File(fileOutputPathName)));
            int b;
            // 将字符输入流中的内容输出出来
            final byte[] bytes = IoUtil.readBytes(bin, false);
            log.info("输入的内容：[{}]", new String(bytes, StandardCharsets.UTF_8));
            // 上述的readBytes方法的参数isCloseStream不能为true，否则会提示异常：java.io.IOException: Stream closed；
            // 导致bin.read读取时，流已经被关闭，可以查看readBytes底层方法。
            while ((b = bin.read()) != -1) {
                bout.write(b);
            }
        } catch (IOException e) {
            log.error("程序发生异常，异常详细信息为：", e);
        } finally {
            // 使用工具类进行关闭，使用的前提必须是需要被关闭的类必须实现Closeable接口
            IoUtil.close(bin);
            IoUtil.close(bout);
        }
    }

    @Test
    @DisplayName("使用1.7语法糖不用关闭情况下测试手动捕获异常")
    @Disabled
    void testManuallyCatchExceptionWithSugar() {
        // 利用jdk 1.7中新增的try-with-resource语法糖来打开资源，而无需手动书写资源来关闭代码。
        // 注意jdk1.7中的变量不能在try()外进行声明后在try()内使用，会提示错误；在jdk1.9中解决了这个问题。
        // 怎么验证呢到底有没有关闭呢？可以查看编译后的结果
        try (BufferedInputStream bin = new BufferedInputStream(new FileInputStream(new File(fileInputPathName)));
             BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(new File(fileOutputPathName)))) {
            int b;
            // 将字符输入流中的内容输出出来
            final byte[] bytes = IoUtil.readBytes(bin, false);
            log.info("输入的内容：[{}]", new String(bytes, StandardCharsets.UTF_8));
            // 上述的readBytes方法的参数isCloseStream不能为true，否则会提示异常：java.io.IOException: Stream closed；
            // 导致bin.read读取时，流已经被关闭，可以查看readBytes底层方法。
            while ((b = bin.read()) != -1) {
                bout.write(b);
            }
        } catch (IOException e) {
            log.error("程序发生异常，异常详细信息为：", e);
        }
    }

    @Test
    @DisplayName("测试自定义模拟stream的关闭")
    @Disabled
    void testCustomSimulateClose() {
        try (SimulateClose simulateClose = new SimulateClose()) {
            // 调用关闭前的操作
            simulateClose.beforeClose();
        } catch (IOException e) {
            log.error("程序发生异常，异常详细信息为：", e);
        }
    }

    @Test
    @DisplayName("测试")
    void testCustomAopException() {

    }
}
