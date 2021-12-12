package com.phoenix.logistics.controller.response;

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
    private Long goodsId;
    private String receiverUsername;
    private String senderUsername;
    private String goodsName;
    private String goodsType;

    public TmpAdminOrder(Long id, Long userOrderId, Integer status, String statusUpdateTime, Long goodsId) {
        this.id = id;
        this.userOrderId = userOrderId;
        this.status = status;
        this.statusUpdateTime = statusUpdateTime;
        this.goodsId = goodsId;
    }
}
