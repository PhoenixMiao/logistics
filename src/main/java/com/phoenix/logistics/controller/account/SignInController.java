package com.phoenix.logistics.controller.account;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.phoenix.logistics.common.Result;
import com.phoenix.logistics.dto.UserDTO;
import com.phoenix.logistics.util.PasswordUtil;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RestController
@Api("登录Controller")
@Validated
public class SignInController {

    @PostMapping("/signIn")
    @ApiOperation("登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码(长度6-20)", required = true, paramType = "query", dataType = "String")
    })
    public Result doLogin(String username,@NotNull @Size(min = 6,max = 20)String password) {

        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return Result.fail("用户名或密码不能为空！");
        }
        AuthenticationToken token = new UsernamePasswordToken(username, PasswordUtil.convert(password));

        try {

            //尝试登陆，将会调用realm的认证方法
            SecurityUtils.getSubject().login(token);

        }catch (AuthenticationException e) {
            if (e instanceof UnknownAccountException) {
                return Result.fail("用户不存在");
            } else if (e instanceof LockedAccountException) {
                return Result.fail("用户被禁用");
            } else if (e instanceof IncorrectCredentialsException) {
                return Result.fail("密码错误");
            } else {
                return Result.fail("用户认证失败");
            }
        }
        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();


        return Result.success("登录成功", principal);
    }

}
