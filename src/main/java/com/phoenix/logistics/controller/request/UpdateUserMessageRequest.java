package com.phoenix.logistics.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("UpdateUserMessageRequest 修改用户信息")
public class UpdateUserMessageRequest {
    @ApiModelProperty("性别")
    private Integer gender;

    @ApiModelProperty("联系电话")
    private String telephone;

    @ApiModelProperty("居住地")
    private String residence;

}
