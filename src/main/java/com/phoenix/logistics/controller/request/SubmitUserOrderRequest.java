package com.phoenix.logistics.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("SubmitUserOrder 提交用户订单")
public class SubmitUserOrderRequest {
    @ApiModelProperty("发送人用户名")
    private String senderUsername;

    @ApiModelProperty("接受人用户名")
    private String receiverUsername;

    @ApiModelProperty("发货地地址")
    private String originLocation;

    @ApiModelProperty("收货地地址")
    private String destinationLocation;

    @ApiModelProperty("发货地经度")
    private Double originLng;

    @ApiModelProperty("发货地纬度")
    private Double originLat;

    @ApiModelProperty("收货地经度")
    private Double destinationLng;

    @ApiModelProperty("收货地经度")
    private Double destinationLat;
}
