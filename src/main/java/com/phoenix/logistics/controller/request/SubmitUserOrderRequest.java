package com.phoenix.logistics.controller.request;

import com.phoenix.logistics.enums.GoodsType;
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
public class SubmitUserOrderRequest{

    @ApiModelProperty("接受人用户名")
    private String receiverUsername;

    @ApiModelProperty("货物名称")
    private String goodsName;

    @ApiModelProperty("货物种类（可选项：DAILY（日用品）、FOOD（食品）、FILE（文件）、CLOTHES（衣物）、DIGITALS（数码产品）、OTHERS（其他）")
    private String goodsType;

    @ApiModelProperty("货物体积（单位：立方米）")
    private Double goodsVolume;

    @ApiModelProperty("货物重量（单位：千克）")
    private Double goodsWeight;

    @ApiModelProperty("货物价值（单位：元）")
    private Double goodsValue;

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
