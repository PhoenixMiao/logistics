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
    @GetMapping("/orderlist")
    @ApiOperation("获取用户订单全部列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize",value = "每页显示数量 (不小于0)",required = true,paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "pageNum", value = "页数 (不小于0)", required = true, paramType = "query", dataType = "Integer")})
    public Result getBriefOrderList(@NotNull @RequestParam("pageSize")Integer pageSize,
                                    @NotNull @RequestParam("pageNum")Integer pageNum){
        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        String username = principal.getUsername();
        return Result.success(userOrderService.getBriefUserOrderList(pageNum,pageSize,username));
    }

    @RequiresRoles("user")
    @GetMapping("/sendorderlist")
    @ApiOperation("获取用户发送的订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize",value = "每页显示数量 (不小于0)",required = true,paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "pageNum", value = "页数 (不小于0)", required = true, paramType = "query", dataType = "Integer")})
    public Result getBriefSendOrderList(@NotNull @RequestParam("pageSize")Integer pageSize,
                                    @NotNull @RequestParam("pageNum")Integer pageNum){
        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        String username = principal.getUsername();
        return Result.success(userOrderService.getBriefUserSendOrderList(pageNum,pageSize,username));
    }

    @RequiresRoles("user")
    @GetMapping("/recieveorderlist")
    @ApiOperation("获取用户接收的订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize",value = "每页显示数量 (不小于0)",required = true,paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "pageNum", value = "页数 (不小于0)", required = true, paramType = "query", dataType = "Integer")})
    public Result getBriefRecieveOrderList(@NotNull @RequestParam("pageSize")Integer pageSize,
                                        @NotNull @RequestParam("pageNum")Integer pageNum){
        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        String username = principal.getUsername();
        return Result.success(userOrderService.getBriefUserRecieveOrderList(pageNum,pageSize,username));
    }

    @RequiresRoles("user")
    @GetMapping("/sendandutlist")
    @ApiOperation("获取用户发送的未处理订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize",value = "每页显示数量 (不小于0)",required = true,paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "pageNum", value = "页数 (不小于0)", required = true, paramType = "query", dataType = "Integer")})
    public Result getBriefSendAndUntreatedOrderList(@NotNull @RequestParam("pageSize")Integer pageSize,
                                           @NotNull @RequestParam("pageNum")Integer pageNum){
        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        String username = principal.getUsername();
        return Result.success(userOrderService.getBriefUserSendAndUntreatedOrderList(pageNum,pageSize,username));
    }

    @RequiresRoles("user")
    @GetMapping("/sendandtrlist")
    @ApiOperation("获取用户发送的运输中订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize",value = "每页显示数量 (不小于0)",required = true,paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "pageNum", value = "页数 (不小于0)", required = true, paramType = "query", dataType = "Integer")})
    public Result getBriefSendAndTransportingedOrderList(@NotNull @RequestParam("pageSize")Integer pageSize,
                                                    @NotNull @RequestParam("pageNum")Integer pageNum){
        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        String username = principal.getUsername();
        return Result.success(userOrderService.getBriefUserSendAndUTransportingOrderList(pageNum,pageSize,username));
    }

    @RequiresRoles("user")
    @GetMapping("/sendandurlist")
    @ApiOperation("获取用户发送的待收货订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize",value = "每页显示数量 (不小于0)",required = true,paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "pageNum", value = "页数 (不小于0)", required = true, paramType = "query", dataType = "Integer")})
    public Result getBriefSendAndUnrecievedOrderList(@NotNull @RequestParam("pageSize")Integer pageSize,
                                                    @NotNull @RequestParam("pageNum")Integer pageNum){
        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        String username = principal.getUsername();
        return Result.success(userOrderService.getBriefUserSendAndUnrecievedOrderList(pageNum,pageSize,username));
    }

    @RequiresRoles("user")
    @GetMapping("/sendandrelist")
    @ApiOperation("获取用户发送的已收货订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize",value = "每页显示数量 (不小于0)",required = true,paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "pageNum", value = "页数 (不小于0)", required = true, paramType = "query", dataType = "Integer")})
    public Result getBriefSendAndRecievedOrderList(@NotNull @RequestParam("pageSize")Integer pageSize,
                                                     @NotNull @RequestParam("pageNum")Integer pageNum){
        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        String username = principal.getUsername();
        return Result.success(userOrderService.getBriefUserSendAndRecievedOrderList(pageNum,pageSize,username));
    }

    @RequiresRoles("user")
    @GetMapping("/recieveandutlist")
    @ApiOperation("获取用户接收的未处理订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize",value = "每页显示数量 (不小于0)",required = true,paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "pageNum", value = "页数 (不小于0)", required = true, paramType = "query", dataType = "Integer")})
    public Result getBriefRecieveAndUntreatedOrderList(@NotNull @RequestParam("pageSize")Integer pageSize,
                                                     @NotNull @RequestParam("pageNum")Integer pageNum){
        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        String username = principal.getUsername();
        return Result.success(userOrderService.getBriefUserRecieveAndUntreatedOrderList(pageNum,pageSize,username));
    }

    @RequiresRoles("user")
    @GetMapping("/recieveandtrlist")
    @ApiOperation("获取用户接收的运输中订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize",value = "每页显示数量 (不小于0)",required = true,paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "pageNum", value = "页数 (不小于0)", required = true, paramType = "query", dataType = "Integer")})
    public Result getBriefRecieveAndTransportingOrderList(@NotNull @RequestParam("pageSize")Integer pageSize,
                                                       @NotNull @RequestParam("pageNum")Integer pageNum){
        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        String username = principal.getUsername();
        return Result.success(userOrderService.getBriefUserRecieveAndTransportingOrderList(pageNum,pageSize,username));
    }

    @RequiresRoles("user")
    @GetMapping("/recieveandurlist")
    @ApiOperation("获取用户接收的待收货订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize",value = "每页显示数量 (不小于0)",required = true,paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "pageNum", value = "页数 (不小于0)", required = true, paramType = "query", dataType = "Integer")})
    public Result getBriefRecieveAndUnrecievedOrderList(@NotNull @RequestParam("pageSize")Integer pageSize,
                                                       @NotNull @RequestParam("pageNum")Integer pageNum){
        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        String username = principal.getUsername();
        return Result.success(userOrderService.getBriefUserRecieveAndUnrecievedOrderList(pageNum,pageSize,username));
    }

    @RequiresRoles("user")
    @GetMapping("/recieveandrelist")
    @ApiOperation("获取用户接收的已收货订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize",value = "每页显示数量 (不小于0)",required = true,paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "pageNum", value = "页数 (不小于0)", required = true, paramType = "query", dataType = "Integer")})
    public Result getBriefRecieveAndRecievedOrderList(@NotNull @RequestParam("pageSize")Integer pageSize,
                                                       @NotNull @RequestParam("pageNum")Integer pageNum){
        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        String username = principal.getUsername();
        return Result.success(userOrderService.getBriefUserRecieveAndRecievedOrderList(pageNum,pageSize,username));
    }
}
