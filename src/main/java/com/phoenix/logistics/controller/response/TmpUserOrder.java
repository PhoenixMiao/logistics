package com.phoenix.logistics.controller.response;

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
    private String relateUsername;
    private Integer status;
    private String statusUpdateTime;
    private Long goodsId;
    private String goodsName;
    private String goodsType;

    public TmpUserOrder(Long id, String senderUsername, String receiverUsername, Integer status, String statusUpdateTime, Long goodsId) {
        this.id = id;
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;
        this.status = status;
        this.statusUpdateTime = statusUpdateTime;
        this.goodsId = goodsId;
    }
}
