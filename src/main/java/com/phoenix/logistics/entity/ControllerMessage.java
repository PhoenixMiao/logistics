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
@ApiModel("ControllerMessage 管理员消息")
public class ControllerMessage {

    @Id
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("管理员id")
    private Long controllerId;

    @ApiModelProperty("新状态")
    private Integer newStatus;

    @ApiModelProperty("更新时间")
    private DateTime updateTime;

    @ApiModelProperty("是否已读")
    private Boolean isRead;
}
