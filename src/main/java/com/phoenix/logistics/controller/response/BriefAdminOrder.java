package com.phoenix.logistics.controller.response;

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
@ApiModel("BriefAdminOrder 管理员订单列表")
public class BriefAdminOrder implements Serializable {
    @ApiModelProperty("管理员订单编号")
    private Long OrderId;

    @ApiModelProperty("发件人用户名")
    private String senderUsername;

    @ApiModelProperty("收件人用户名")
    private String receiverUsername;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("状态更新时间")
    private String statusUpdateTime;

    @ApiModelProperty("货物名称")
    private String goodsName;

    @ApiModelProperty("货物种类")
    private String goodsType;
}
