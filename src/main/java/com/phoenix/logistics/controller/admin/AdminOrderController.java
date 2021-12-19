package com.phoenix.logistics.controller.admin;

import com.phoenix.logistics.common.Result;
import com.phoenix.logistics.dto.UserDTO;
import com.phoenix.logistics.mapper.AdminOrderMapper;
import com.phoenix.logistics.service.admin.AdminOrderService;
import com.phoenix.logistics.service.user.UserOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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

    @Autowired
    AdminOrderMapper adminOrderMapper;

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

    @RequiresRoles("admin")
    @GetMapping("/detail")
    @ApiOperation("根据adminOrderId获取订单详情")
    @ApiImplicitParam(name = "id", value = "管理员订单id", required = true, paramType = "query", dataType = "Long")
    public Result detail(@NotNull@RequestParam("id")Long id) {
        return Result.success("获取成功", adminOrderService.getOrderDetailResponse(id));
    }


    @RequiresRoles("admin")
    @GetMapping("/list")
    @ApiOperation("根据订单状态分类获取管理员订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize",value = "每页显示数量 (不小于0)",required = true,paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "pageNum", value = "页数 (不小于0)", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "status",value = "要查询的订单状态,用整数表示 0待发货,1运输中,2待收货,3已收货,4全部订单",required = true,paramType = "query",dataType = "Integer")})
    public Result getBriefAdminOrderListByStatus(@NotNull @RequestParam("pageSize")Integer pageSize,
                                    @NotNull @RequestParam("pageNum")Integer pageNum,
                                                 @NotNull @RequestParam("status")Integer status){
        return Result.success(adminOrderService.getBriefAdminOrderListByStatus(pageNum,pageSize,status));
    }



    @RequiresRoles("admin")
    @GetMapping("/message")
    @ApiOperation("获取管理员信息列表")
    public Result getAdminMessageList(){
        return Result.success("获取成功",adminOrderService.getAdminMessageList());
    }


    @RequiresRoles("admin")
    @GetMapping("/search")
    @ApiOperation("根据订单id进行模糊搜索")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize",value = "每页显示数量 (不小于0)",required = true,paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "pageNum", value = "页数 (不小于0)", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "id",value = "搜索框里输入的内容",required = true,paramType = "query",dataType = "Integer")})
    public Result searchAdminOrder(@NotNull @RequestParam("pageSize")Integer pageSize,
                                   @NotNull @RequestParam("pageNum")Integer pageNum,
                                   @RequestParam("id") Integer id){
        return Result.success("获取成功",adminOrderService.searchAdminOrder(pageNum,pageSize,id));
    }
}
