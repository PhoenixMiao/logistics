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
@ApiModel("UserMessage 用户消息")
public class UserMessage {

    @Id
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("新状态")
    private Integer newStatus;

    @ApiModelProperty("更新时间")
    private DateTime updateTime;

    @ApiModelProperty("是否已读")
    private Boolean isRead;


}
