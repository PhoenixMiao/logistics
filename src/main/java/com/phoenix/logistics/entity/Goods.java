package com.phoenix.logistics.entity;

import com.phoenix.logistics.enums.GoodsType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("Goods 货物")
public class Goods implements Serializable
{
    @Id
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("种类")
    private GoodsType type;

    @ApiModelProperty("体积")
    private Double volume;

    @ApiModelProperty("重量")
    private Double weight;

    @ApiModelProperty("价值")
    private Double value;

    public Goods(String name, GoodsType type, Double volume, Double weight, Double value) {
        this.name = name;
        this.type = type;
        this.volume = volume;
        this.weight = weight;
        this.value = value;
    }
}
