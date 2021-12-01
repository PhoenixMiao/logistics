package com.phoenix.logistics.controller.homepage;

import com.phoenix.logistics.common.Result;
import com.phoenix.logistics.dto.UserDTO;
import com.phoenix.logistics.service.homepage.CollectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@Api("收藏模块控制器")
@RequestMapping("/collection")
public class CollectionController {

    @Autowired
    CollectionService collectionService;

    @RequiresRoles("company")
    @GetMapping("/group_names")
    @ApiOperation("查看我的分组")
    public Result getMyGroup_names(){
        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        int user_id = principal.getId();
        return Result.success(collectionService.getMyGroup_names(user_id));
    }

    @RequiresRoles("company")
    @PostMapping("/group")
    @ApiOperation("新建分组")
    @ApiImplicitParam(name = "group_name",value = "分组名")
    public Result addGroup(@NotBlank @RequestParam("group_name")String group_name){
        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        int user_id = principal.getId();
        return Result.success(collectionService.addGroup(user_id,group_name));
    }

//    @PostMapping("")
//    @ApiOperation("收藏一个公司，并放到分组里，如果分组不存在就新建")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "company_url",value = "公司网络地址"),
//            @ApiImplicitParam(name = "group_name",value = "分组名")
//    })
//    public Result addCollection(@RequestParam("company_url")String company_url,
//                                @NotBlank @RequestParam("group_name")String group_name){
//        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
//        int user_id = principal.getId();
//        return Result.success(collectionService.addCollection(user_id,company_url,group_name));
//    }

    @RequiresRoles("company")
    @PostMapping("")
    @ApiOperation("收藏一个公司，并放到分组里，如果分组不存在就新建")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "company_id",value = "公司id"),
            @ApiImplicitParam(name = "group_name",value = "分组名")
    })
    public Result addCollection(@RequestParam("company_id")Long company_id,
                                @NotBlank @RequestParam("group_name")String group_name){
        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        int user_id = principal.getId();
        return Result.success(collectionService.addCollection(user_id,company_id,group_name));
    }

    @RequiresRoles("company")
    @GetMapping("")
    @ApiOperation("查看某个分组的收藏基本信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "group_name",value = "分组名"),
            @ApiImplicitParam(name = "pageNum",value = "页码"),
            @ApiImplicitParam(name = "pageSize",value = "一页条目数")
    })
    public Result getCollections(@NotBlank @RequestParam("group_name")String group_name,
                                @NotNull @RequestParam("pageNum")Integer pageNum,
                                 @NotNull @RequestParam("pageSize")Integer pageSize){
        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        int user_id = principal.getId();
        return Result.success(collectionService.getCollections(user_id,group_name,pageNum,pageSize));
    }

    @RequiresRoles("company")
    @GetMapping("/detail")
    @ApiOperation("查看某个分组的收藏公司重点信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "group_name",value = "分组名"),
            @ApiImplicitParam(name = "pageNum",value = "页码"),
            @ApiImplicitParam(name = "pageSize",value = "一页条目数")
    })
    public Result getMemberCollections(@NotBlank @RequestParam("group_name")String group_name,
                                 @NotNull @RequestParam("pageNum")Integer pageNum,
                                 @NotNull @RequestParam("pageSize")Integer pageSize){
        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        int user_id = principal.getId();
        return Result.success(collectionService.getCollectionDTOs(user_id,group_name,pageNum,pageSize));
    }

    @RequiresRoles("company")
    @PostMapping("/setTop")
    @ApiOperation("置顶收藏")
    @ApiImplicitParam(name = "collection_id",value = "收藏id")
    public Result setTop(@NotNull @RequestParam("collection_id")Integer collection_id){
        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        int user_id = principal.getId();
        collectionService.setTop(user_id,collection_id);
        return Result.success(null);
    }

    @RequiresRoles("company")
    @PostMapping("/removeTop")
    @ApiOperation("取消置顶")
    @ApiImplicitParam(name = "collection_id",value = "收藏id")
    public Result removeTop(@NotNull @RequestParam("collection_id")Integer collection_id){
        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        int user_id = principal.getId();
        collectionService.removeTop(user_id,collection_id);
        return Result.success(null);
    }

    @RequiresRoles("company")
    @PostMapping("/removeCollection")
    @ApiOperation("取消收藏")
    @ApiImplicitParam(name = "collection_id",value = "收藏id")
    public Result removeCollection(@NotNull @RequestParam("collection_id")Integer collection_id){
        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        int user_id = principal.getId();
        collectionService.removeCollection(user_id,collection_id);
        return Result.success(null);
    }

    @RequiresRoles("company")
    @GetMapping("/company_url")
    @ApiOperation("打开收藏夹中的公司网址，算积分用")
    @ApiImplicitParam(name = "collection_id",value = "收藏id")
    public Result openCompany_url(@NotNull @RequestParam("collection_id")Integer collection_id){
        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        int user_id = principal.getId();
        return Result.success(collectionService.openCompany_url(user_id,collection_id));
    }

}
