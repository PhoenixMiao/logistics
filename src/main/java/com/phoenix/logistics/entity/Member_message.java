package com.phoenix.logistics.entity;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@ApiModel("会员系统通知（积分变化等）")
public class Member_message {

    private Long id;
    private Integer user_id;
    private String content;
    private Timestamp creatime;

    public Member_message(){
        creatime = new Timestamp(System.currentTimeMillis());
    }


}
