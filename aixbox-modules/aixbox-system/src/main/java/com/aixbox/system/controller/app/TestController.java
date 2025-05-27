package com.aixbox.system.controller.app;


import com.aixbox.common.core.pojo.CommonResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.aixbox.common.core.pojo.CommonResult.success;

/**
 *
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/test")
public class TestController {

    @GetMapping()
    public CommonResult<String> getSysDept() {
        return success("测试");
    }

}
