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
@ApiModel("ControllerOrder 管理员订单")
public class AdminOrder {

    @Id
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("用户订单id")
    private Long userOrderId;

    @ApiModelProperty("货物id")
    private Long goodsId;

    @ApiModelProperty("车辆id")
    private Long carId;

    @ApiModelProperty("司机id")
    private Long driverId;

    @ApiModelProperty("管理员用户名")
    private String adminUsername;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("状态更新时间")
    private String statusUpdateTime;

    @ApiModelProperty("预计运输时间（以小时为单位）")
    private Integer transportTime;

    @ApiModelProperty("发货时间")
    private String sendTime;

    @ApiModelProperty("送达时间")
    private String arriveTime;

    public AdminOrder(Long userOrderId, Long goodsId, Integer status, String statusUpdateTime,Integer transportTime) {
        this.userOrderId = userOrderId;
        this.goodsId = goodsId;
        this.status = status;
        this.statusUpdateTime = statusUpdateTime;
        this.transportTime = transportTime;
    }
}
