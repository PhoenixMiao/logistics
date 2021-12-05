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

    @ApiModelProperty("管理员订单id")
    private Long adminOrderId;

    @ApiModelProperty("发送人用户名")
    private String senderUsername;

    @ApiModelProperty("接受人用户名")
    private String receiverUsername;

    @ApiModelProperty("货物id")
    private Long goodsId;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("状态更新时间")
    private String statusUpdateTime;

    @ApiModelProperty("发货地地址")
    private String originLocation;

    @ApiModelProperty("收货地地址")
    private String destinationLocation;

    @ApiModelProperty("提交时间")
    private String commitTime;

    @ApiModelProperty("收货时间")
    private String receiveTime;

    @ApiModelProperty("预计运送时间")
    private Integer transportTime;

    public UserOrder(String senderUsername, String reciverUsername, Long goodsId, Integer status,String statusUpdateTime, String originLocation, String destinationLocation, String commitTime, Integer transportTime) {
        this.senderUsername = senderUsername;
        this.receiverUsername = reciverUsername;
        this.goodsId = goodsId;
        this.status = status;
        this.statusUpdateTime = statusUpdateTime;
        this.originLocation = originLocation;
        this.destinationLocation = destinationLocation;
        this.commitTime = commitTime;
        this.transportTime = transportTime;
    }
}
