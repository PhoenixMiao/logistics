package com.phoenix.logistics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuyHistoryDTO {

    private Integer id;
    private Integer user_id;
    private String username;
    private String content;
    private Double price;
    private Timestamp creatime;
    private String type;
    private String order_id;
    private String business_id;

}
