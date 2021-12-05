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
@ApiModel("Driver 司机")
public class Driver {

    @Id
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("状态")
    private Integer status;
}
