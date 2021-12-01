package com.phoenix.logistics.controller;

import com.phoenix.logistics.common.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UnAuthController {

    @RequestMapping(value = "/unauth")
    public Result unauth() {
        return Result.fail("未登录");
    }

}
