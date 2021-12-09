package com.phoenix.logistics.controller.response;

import com.phoenix.logistics.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("GetUserResponse 用户信息返回")
public class GetUserResponse implements Serializable {
    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("性别")
    private Integer gender;

    @ApiModelProperty("联系电话")
    private String telephone;

    @ApiModelProperty("居住地")
    private String residence;

    @ApiModelProperty("类别")
    private Integer type;

    public GetUserResponse(User user, Integer type){
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.telephone = user.getTelephone();
        this.residence = user.getResidence();
        this.type = type;
    }
}
