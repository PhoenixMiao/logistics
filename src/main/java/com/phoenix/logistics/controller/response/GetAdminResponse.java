package com.phoenix.logistics.controller.response;

import com.phoenix.logistics.entity.Admin;
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
@ApiModel("GetAdminrResponse 管理员信息返回")
public class GetAdminResponse implements Serializable {
    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("类别")
    private Integer type;

    public GetAdminResponse(Admin admin, Integer type){
        this.id = admin.getId();
        this.username = admin.getUsername();
        this.password = admin.getPassword();
        this.type = type;
    }
}
