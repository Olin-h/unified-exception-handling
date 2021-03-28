package git.olin.execption.controller;

import git.olin.execption.annotation.ParamCheck;
import git.olin.execption.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 参数检测控制器
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2021-03-28
 */
@RequestMapping("/param-check")
@RestController
@Slf4j
public class ParamCheckController {

    @GetMapping(value = "/sayHello1")
    public R<String> sayHello1(@RequestParam String name) {
        log.info("Hello, {}！", name);
        return R.success();
    }

    @GetMapping(value = "/sayHello2")
    public R<String> sayHello2(@ParamCheck String name) {
        log.info("Hello, {}！", name);
        return R.success();
    }

    @GetMapping(value = "/sayHello3")
    public R<String> sayHello3(@RequestParam @ParamCheck String name) {
        log.info("Hello, {}！", name);
        return R.success();
    }
}
