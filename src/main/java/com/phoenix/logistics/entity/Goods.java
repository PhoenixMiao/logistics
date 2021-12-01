package com.phoenix.logistics.entity;

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
@ApiModel("Goods 货物")
public class Goods
{
    @Id
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("种类")
    private Integer type;

    @ApiModelProperty("体积")
    private Double volume;

    @ApiModelProperty("重量")
    private Double weight;

    @ApiModelProperty("价值")
    private Double value;
}
