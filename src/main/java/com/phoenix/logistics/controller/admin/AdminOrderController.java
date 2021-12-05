package com.phoenix.logistics.controller.admin;

import com.phoenix.logistics.common.Result;
import com.phoenix.logistics.dto.UserDTO;
import com.phoenix.logistics.service.admin.AdminOrderService;
import com.phoenix.logistics.service.user.UserOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@Api("管理员订单控制器")
@RequestMapping("/admin_order")
@Validated
public class AdminOrderController {
    @Autowired
    AdminOrderService adminOrderService;

    @RequiresRoles("admin")
    @PostMapping("/deal")
    @ApiOperation("处理新提交的订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "所要处理的订单的id",required = true,paramType = "query",dataType = "Long"),
            @ApiImplicitParam(name = "carId", value = "货车id", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "driverId", value = "司机id", required = true, paramType = "query", dataType = "Long")})
    public Result DealAdminOrder(@NotNull @RequestParam("id")Long id,@NotNull@RequestParam("carId")Long carId,@NotNull@RequestParam("driverId")Long driverId){
        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        String username = principal.getUsername();
        adminOrderService.dealAdminOrder(id,carId,driverId,username);
        return Result.success("分配成功");
    }

    @PostMapping("/arrive")
    @ApiOperation("货物送到了")
    @ApiImplicitParam(name="id",value = "送达的管理员订单id",required = true,paramType = "query",dataType = "Long")
    public Result goodsArrive(@NotNull@RequestParam("id")Long id){
        if(adminOrderService.goodsArrive(id)==0) return Result.fail("货物尚未处理");
        return Result.success("货物已到达");
    }

    @GetMapping("/{id}")
    @ApiOperation("通过id获取订单详情")
    public Result getAdminOrderById(@PathVariable("id")Long id){
        return Result.success("获取成功！！",adminOrderService.getAdminOrderById(id));
    }



}
