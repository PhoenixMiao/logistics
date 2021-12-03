package com.phoenix.logistics.controller.user;

import com.phoenix.logistics.common.Result;
import com.phoenix.logistics.controller.request.SubmitUserOrderRequest;
import com.phoenix.logistics.service.user.UserOrderService;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@Api("用户订单控制器")
@RequestMapping("/user_order")
@Validated
public class UserOrderController {

    @Autowired
    private UserOrderService userOrderService;

    @RequiresRoles("user")
    @PostMapping("/submit")
    @ApiOperation("提交用户订单")
    public Result SubmitUserOrder(@NotNull @Valid @RequestBody SubmitUserOrderRequest submitUserOrderRequest){
        userOrderService.submitUserOrder(submitUserOrderRequest);
        return Result.success("提交成功");
    }
}
