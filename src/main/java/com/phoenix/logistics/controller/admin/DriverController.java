package com.phoenix.logistics.controller.admin;

import com.phoenix.logistics.common.PageParam;
import com.phoenix.logistics.common.Result;
import com.phoenix.logistics.exception.RRException;
import com.phoenix.logistics.service.admin.DriverService;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@Api("管理员司机控制器")
@RequestMapping("/driver")
@Validated
public class DriverController {

    @Autowired
    private DriverService driverService;

    @RequiresRoles("admin")
    @GetMapping("/all")
    @ApiOperation("查看所有司机")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize",value = "每页显示数量 (不小于0)",required = true,paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "pageNum", value = "页数 (不小于0)", required = true, paramType = "query", dataType = "Integer")})
    public Result getAllDrivers(@NotNull @RequestParam("pageSize")Integer pageSize,
                             @NotNull @RequestParam("pageNum")Integer pageNum){
        return Result.success(driverService.getAllDrivers(pageNum,pageSize));
    }

    @RequiresRoles("admin")
    @PostMapping("/add")
    @ApiOperation("添加司机")
    @ApiImplicitParam(name = "num",value = "添加的车辆数量（不写默认为1）",paramType = "query",dataType = "Integer")
    public Result AddDriver(@RequestParam(value = "num",defaultValue = "1")Integer num){
        driverService.addDriver(num);
        return Result.success("添加成功");
    }

    @RequiresRoles("admin")
    @PostMapping("/delete")
    @ApiOperation("删除司机")
    @ApiImplicitParam(name = "id",value = "所要删除司机的id",paramType = "query",dataType = "Long")
    public Result DeleteDriver(@RequestParam(value = "id")Long id){
        try{
            driverService.deleteDriver(id);
        }catch (RRException e){
            if(e.getEnumExceptionType().getErrorCode()==1005)
                return Result.fail("该司机正在驾驶车辆，目前不能删除");
        }
        return Result.success("删除成功");
    }

    @RequiresRoles("admin")
    @GetMapping("/free")
    @ApiOperation("获取空闲车辆的列表")
    public Result getAllFreeCars(@NotNull @Valid @RequestBody PageParam pageParam) {
        if(pageParam.getPageNum()==null && pageParam.getPageSize()==null) return Result.success("获得成功",driverService.getAllFreeDrivers());
        return Result.success("获取成功",driverService.getAllFreeDrivers(pageParam.getPageNum(), pageParam.getPageSize()));
    }
}
