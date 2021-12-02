package com.phoenix.logistics.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("User 用户")
public class User
{
    @Id
    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("用户名（字面量与id相同）")
    private String userName;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("性别")
    private Integer gender;

    @ApiModelProperty("联系电话")
    private String telephone;

    @ApiModelProperty("居住地")
    private String residence;

    @ApiModelProperty("头像")
    private String portrait;

}
