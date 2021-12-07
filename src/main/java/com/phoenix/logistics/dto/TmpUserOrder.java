package com.phoenix.logistics.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
public class TmpUserOrder implements Serializable {
    private Long id;
    private String senderUsername;
    private String receiverUsername;
    private Integer status;
    private String statusUpdateTime;
}
