package com.phoenix.logistics.entity;


import cn.hutool.core.date.DateTime;
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
@ApiModel("UserOrder 用户订单")
public class UserOrder {
    @Id
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("发送人id")
    private Long senderId;

    @ApiModelProperty("接受人id")
    private Long receierId;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("发货地经度")
    private Double originLng;

    @ApiModelProperty("发货地纬度")
    private Double originLat;

    @ApiModelProperty("收货地经度")
    private Double destinationLng;

    @ApiModelProperty("收货地经度")
    private Double destinationLat;

    @ApiModelProperty("提交时间")
    private DateTime commitTime;

    @ApiModelProperty("收货时间")
    private DateTime receiveTime;

}
