package com.phoenix.logistics.controller.account;

import com.aliyuncs.exceptions.ClientException;
import com.google.code.kaptcha.Producer;
import com.phoenix.logistics.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.phoenix.logistics.service.account.SignUpService;
import com.phoenix.logistics.util.RegexUtil;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

@Controller
@Api("注册Controller")
@RequestMapping("/signUp")
@Validated
public class SignUpController {

    @Autowired
    public SignUpService signUpService;

    @ResponseBody
    @PostMapping("singUp")
    @ApiOperation("注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名（3到10个字）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码（6到20位）", required = true, paramType = "query", dataType = "String")})
    public Result signUp(@NotNull @Size(max = 10,min = 3) @RequestParam("username")String username,
            @NotNull @Size(max = 20,min = 6) @RequestParam("password")String password){
        signUpService.signUp(username,password);
        return Result.success("注册成功!");
    }




//    @Autowired
//    UpgradeAuthService upgradeAuthService;
//
//    @PostMapping("/upgrade")
//    @ApiOperation("普通用户申请提升为企业账号")
//    public Result upgrade() {
//        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
//        String username = principal.getUsername();
//        upgradeAuthService.upgradeAuth(username);
//        return Result.success("升级账号成功，重新登录后生效");
//    }

}
