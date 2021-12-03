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

    @ApiModelProperty("发送人用户名")
    private String senderUsername;

    @ApiModelProperty("接受人用户名")
    private String reciverUsername;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("发货地地址")
    private String originLocation;

    @ApiModelProperty("收货地地址")
    private String destinationLocation;

    @ApiModelProperty("提交时间")
    private DateTime commitTime;

    @ApiModelProperty("收货时间")
    private DateTime receiveTime;

    @ApiModelProperty("预计运送时间")
    private Integer transportTime;
}
