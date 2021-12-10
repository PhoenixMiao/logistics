package com.phoenix.logistics.controller.user;

import com.phoenix.logistics.common.Result;
import com.phoenix.logistics.controller.request.SubmitUserOrderRequest;
import com.phoenix.logistics.dto.UserDTO;
import com.phoenix.logistics.entity.AdminOrder;
import com.phoenix.logistics.entity.UserOrder;
import com.phoenix.logistics.service.admin.AdminOrderService;
import com.phoenix.logistics.service.user.UserOrderService;
import io.swagger.annotations.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;

@RestController
@Api("用户订单控制器")
@RequestMapping("/user_order")
@Validated
public class UserOrderController {

    @Autowired
    private UserOrderService userOrderService;

    @Autowired
    private AdminOrderService adminOrderService;

    @RequiresRoles("user")
    @PostMapping("/submit")
    @ApiOperation("提交用户订单")
    public Result SubmitUserOrder(@NotNull @Valid @RequestBody SubmitUserOrderRequest submitUserOrderRequest){
        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        String username = principal.getUsername();
        if(username.equals(submitUserOrderRequest.getReceiverUsername())){
            return Result.fail("收件人不能是自己！！");
        }
        HashMap<String,Long> data = userOrderService.submitUserOrder(username,submitUserOrderRequest);
        return Result.success("提交成功",data);
    }


    @RequiresRoles("user")
    @PostMapping("/receive")
    @ApiOperation("用户确认收货")
    @ApiImplicitParam(name = "id",value = "处理的用户订单的id",paramType = "query",dataType = "Long")
    public Result receiveGoods(@NotNull@RequestParam(value = "id")Long id){
        if(userOrderService.receiveGoods(id)==0) return Result.fail("货物未处理或未送达");
        return Result.success("收货成功");
    }

    @RequiresRoles("user")
    @GetMapping("/detail")
    @ApiOperation("根据userOrderId获取订单详情")
    @ApiImplicitParam(name = "id", value = "用户订单id", required = true, paramType = "query", dataType = "Long")
    public Result detail(@NotNull@RequestParam("id")Long id){
        return Result.success("获取成功",adminOrderService.getOrderDetailResponse(userOrderService.getUserOrderById(id).getAdminOrderId()));
    }

    @RequiresRoles("user")
    @GetMapping("/list")
    @ApiOperation("根据条件获取用户发送的订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize",value = "每页显示数量 (不小于0)",required = true,paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "pageNum", value = "页数 (不小于0)", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "sendOrRecieve", value = "表示要获取用户发出或接收的订单 0表示发出 1表示接收", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "status",value = "要查询的订单状态,用整数表示 0待发货,1运输中,2待收货,3已收货",required = true,paramType = "query",dataType = "Integer")})
    public Result getBriefSendOrderList(@NotNull @RequestParam("pageSize")Integer pageSize,
                                    @NotNull @RequestParam("pageNum")Integer pageNum,
                                        @NotNull @RequestParam("sendOrRecieve")Integer sendOrRecieve,
                                        @NotNull @RequestParam("status")Integer status){
        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        String username = principal.getUsername();
        return Result.success(userOrderService.getBriefUserOrderListByCondition(pageNum,pageSize,username,sendOrRecieve,status));
    }

    @RequiresRoles("user")
    @GetMapping("/message")
    @ApiOperation("获取用户信息列表")
    public Result getUserMessageList(){
        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        String username = principal.getUsername();
        return Result.success(userOrderService.getUserMessageList(username));
    }
}
