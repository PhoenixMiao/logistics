package com.phoenix.logistics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Buy_historyStatics {

    //充值会员数（购买条数）
    private Integer buy_times;
    private Double total_price;

}
