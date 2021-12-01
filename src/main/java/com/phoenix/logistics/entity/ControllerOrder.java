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
public class ControllerOrder {

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
    private Long staffId;

    @ApiModelProperty("管理人员的id")
    private Long controllerId;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("预计运送时间")
    private Integer transportTime;

    @ApiModelProperty("发货时间")
    private DateTime sendTime;

    @ApiModelProperty("送达时间")
    private DateTime arriveTime;
}
