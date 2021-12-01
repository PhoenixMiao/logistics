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

    @ApiModelProperty("创建时间")
    private String create_time;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("性别")
    private Integer gender;

    @ApiModelProperty("联系电话")
    private String telephone;

    @ApiModelProperty("居住地")
    private String residence;

    @ApiModelProperty("头像")
    private String portrait;
}
