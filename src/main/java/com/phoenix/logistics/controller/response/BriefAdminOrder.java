package com.phoenix.logistics.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@ApiModel("BriefAdminOrder 管理员订单列表")
public class BriefAdminOrder {
    @ApiModelProperty("管理员订单编号")
    private Long adminOrderId;

    @ApiModelProperty("发件人用户名")
    private String senderUsername;

    @ApiModelProperty("收件人用户名")
    private String receiverUsername;

    @ApiModelProperty("状态")
    private Integer status;
}
