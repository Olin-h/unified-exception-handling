package git.olin.execption.handler.controller;

import git.olin.execption.handler.annotation.ParamCheck;
import org.springframework.stereotype.Controller;
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
public class ParamCheckController {

    @GetMapping(value = "/output/{msg}")
    public String outputMessage(@ParamCheck @PathVariable("msg") String msg) {
        return msg;
    }
}
