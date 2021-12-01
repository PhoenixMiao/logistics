package com.phoenix.logistics.controller.account;

import com.phoenix.logistics.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Api("登出Controller")
public class SignOutController {

    @PostMapping("/signOut")
    @ApiOperation("登出")
    public Result logout() throws IOException {
        SecurityUtils.getSubject().logout();
        return Result.success(null);
    }

}
