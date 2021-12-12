package com.phoenix.logistics.controller.admin;

import com.phoenix.logistics.common.PageParam;
import com.phoenix.logistics.common.Result;
import com.phoenix.logistics.service.admin.CarService;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@Api("管理员车辆控制器")
@RequestMapping("/car")
@Validated
public class CarController {

    @Autowired
    private CarService carService;

    @RequiresRoles("admin")
    @GetMapping("/all")
    @ApiOperation("查看所有车辆")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize",value = "每页显示数量 (不小于0)",required = true,paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "pageNum", value = "页数 (不小于0)", required = true, paramType = "query", dataType = "Integer")})
    public Result getAllCars(@NotNull @RequestParam("pageSize")Integer pageSize,
                         @NotNull @RequestParam("pageNum")Integer pageNum){
        return Result.success(carService.getAllCars(pageNum,pageSize));
    }

    @RequiresRoles("admin")
    @PostMapping("/add")
    @ApiOperation("添加车辆")
    @ApiImplicitParam(name = "num",value = "添加的车辆数量（不写默认为1）",paramType = "query",dataType = "Integer")
    public Result AddCar(@RequestParam(value = "num",defaultValue = "1")Integer num){
        carService.addCar(num);
        return Result.success("添加成功");
    }

    @RequiresRoles("admin")
    @PostMapping("/delete")
    @ApiOperation("删除车辆")
    @ApiImplicitParam(name = "id",value = "所要删除车辆的id",paramType = "query",dataType = "Long")
    public Result DeleteCar(@RequestParam(value = "id")Long id){
        if(carService.deleteCar(id) == 1)
            return Result.fail("该车辆正在被使用，目前不能删除");
        else return Result.success("删除成功");
    }

    @RequiresRoles("admin")
    @GetMapping("/free")
    @ApiOperation("获取空闲车辆的列表")
    public Result getAllFreeCars(@NotNull @Valid @RequestBody PageParam pageParam) {
        if(pageParam.getPageNum()==null && pageParam.getPageSize()==null) return Result.success("获得成功",carService.getAllFreeCars());
       return Result.success("获取成功",carService.getAllFreeCars(pageParam.getPageNum(), pageParam.getPageSize()));
    }

}
