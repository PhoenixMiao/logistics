package com.phoenix.logistics.controller.admin;

import com.phoenix.logistics.common.Result;
import com.phoenix.logistics.service.admin.DriverService;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@Api("管理员司机控制器")
@RequestMapping("/driver")
@Validated
public class DriverController {

    @Autowired
    private DriverService DriverService;

    @RequiresRoles("admin")
    @GetMapping("/all")
    @ApiOperation("查看所有司机")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize",value = "每页显示数量 (不小于0)",required = true,paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "pageNum", value = "页数 (不小于0)", required = true, paramType = "query", dataType = "Integer")})
    public Result getAllDrivers(@NotNull @RequestParam("pageSize")Integer pageSize,
                             @NotNull @RequestParam("pageNum")Integer pageNum){
        return Result.success(DriverService.getAllDrivers(pageNum,pageSize));
    }

    @RequiresRoles("admin")
    @PostMapping("/add")
    @ApiOperation("添加司机")
    @ApiImplicitParam(name = "num",value = "添加的车辆数量（不写默认为1）",paramType = "query",dataType = "Integer")
    public Result AddDriver(@RequestParam(value = "num",defaultValue = "1")Integer num){
        DriverService.addDriver(num);
        return Result.success("添加成功");
    }

    @RequiresRoles("admin")
    @PostMapping("/delete")
    @ApiOperation("删除司机")
    @ApiImplicitParam(name = "id",value = "所要删除司机的id",paramType = "query",dataType = "Long")
    public Result DeleteDriver(@RequestParam(value = "id")Long id){
        if(DriverService.deleteDriver(id) == 1)
            return Result.fail("该司机正在驾驶车辆，目前不能删除");
        else return Result.success("删除成功");
    }
}
