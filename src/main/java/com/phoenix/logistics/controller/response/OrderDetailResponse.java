package com.phoenix.logistics.controller.response;

import com.phoenix.logistics.enums.GoodsType;
import io.swagger.annotations.Api;
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
@ApiModel("OrderDetailResponse 订单详情的所有信息")
public class OrderDetailResponse implements Serializable {
    @ApiModelProperty("货物id")
    private Long goodsId;

    @ApiModelProperty("名称")
    private String goodsName;

    @ApiModelProperty("体积")
    private Double goodsVolume;

    @ApiModelProperty("重量")
    private Double goodsWeight;

    @ApiModelProperty("价值")
    private Double goodsValue;

    @ApiModelProperty("发送人用户名")
    private String senderUsername;

    @ApiModelProperty("接受人用户名")
    private String receiverUsername;

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

    @ApiModelProperty("车辆id")
    private Long carId;

    @ApiModelProperty("司机id")
    private Long driverId;

    @ApiModelProperty("管理员用户名")
    private String adminUsername;

    @ApiModelProperty("预计运输时间（以小时为单位）")
    private Integer transportTime;

    @ApiModelProperty("发货时间")
    private String sendTime;

    @ApiModelProperty("送达时间")
    private String arriveTime;
}
