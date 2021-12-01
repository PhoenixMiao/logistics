package com.phoenix.logistics.controller.message;

import com.aliyuncs.exceptions.ClientException;
import com.phoenix.logistics.common.Result;
import com.phoenix.logistics.dto.UserDTO;
import com.phoenix.logistics.service.message.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@Api("通知消息控制器")
@RequestMapping("/message")
@Validated
@Slf4j
public class MessageController {

    @Autowired
    MessageService messageService;

    @RequiresRoles("user")
    @PostMapping("/content")
    @ApiOperation("提醒公司更新报价")
    @ApiImplicitParam(name = "id",value = "公司id")
    public Result remindUpdateContent(@NotNull @RequestParam("id")Long id) {
        try {
            messageService.remindUpdateContent(id);
        } catch (ClientException e) {
            log.error("短信发送失败");
        }
        return Result.success(null);
    }

    @RequiresRoles("company")
    @GetMapping("/content")
    @ApiOperation("获取是否有被提醒更新报价的通知")
    public Result getRemindUpdateContent() {
        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        int user_id = principal.getId();
        return Result.success(messageService.getRemindUpdateContent(user_id));
    }

    @RequiresRoles("company")
    @DeleteMapping("/content")
    @ApiOperation("更新消息至已读状态")
    public Result readMessage() {
        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        int user_id = principal.getId();
        messageService.readMessage(user_id);
        return Result.success(null);
    }

}
