package com.phoenix.logistics.controller.member;

import com.phoenix.logistics.common.Result;
import com.phoenix.logistics.dto.UserDTO;
import com.phoenix.logistics.service.member.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@RestController
@Api("会员系统控制器")
@RequestMapping("/member")
public class MemberController {

    @Autowired
    MemberService memberService;

    @RequiresRoles("user")
    @GetMapping("")
    @ApiOperation("获取会员信息（积分等）")
    public Result getMemberInfo(){
        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        int user_id = principal.getId();
        return Result.success(memberService.getMemberInfo(user_id));
    }

    @RequiresRoles("user")
    @GetMapping("/message")
    @ApiOperation("获取积分消息变更通知")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页码"),
            @ApiImplicitParam(name = "pageSize",value = "一页条目数")
    })
    public Result getMember_messages(@NotNull @RequestParam("pageNum")Integer pageNum,
                                     @NotNull @RequestParam("pageSize")Integer pageSize){
        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        int user_id = principal.getId();
        return Result.success(memberService.getMember_messages(user_id,pageNum,pageSize));
    }

    @RequiresRoles("user")
    @GetMapping("/exchangeMember")
    @ApiOperation("积分兑换会员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "month",value = "兑换会员月数")
    })
    public Result exchangeMember(@NotNull @Positive @RequestParam("month")Integer month){
        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        int user_id = principal.getId();
        return Result.success(memberService.exchangeMember(user_id,month));
    }

}