package com.phoenix.logistics.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
public class TmpAdminOrder implements Serializable {
    private Long id;
    private Long userOrderId;
    private Integer status;
    private String statusUpdateTime;
    private Integer isRead;
}
