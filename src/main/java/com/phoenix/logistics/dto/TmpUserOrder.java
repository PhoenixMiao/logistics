package com.phoenix.logistics.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TmpUserOrder {
    private Long Id;
    private String senderUsername;
    private String receiverUsername;
    private Integer status;
    private String statusUpdateTime;
}
