package com.phoenix.logistics.mapper;

import com.phoenix.logistics.MyMapper;
import org.apache.ibatis.annotations.Select;
import com.phoenix.logistics.dto.BuyHistoryDTO;
import com.phoenix.logistics.entity.Buy_history;

import java.sql.Timestamp;
import java.util.List;

public interface Buy_historyMapper extends MyMapper<Buy_history> {

    @Select("SELECT b.id,user_id,user_name username,content,price,creatime,type,order_id,business_id " +
            "FROM buy_history b,tb_user t " +
            "WHERE b.user_id = t.id ORDER BY creatime DESC")
    List<BuyHistoryDTO> getAll();

    //获取当日充值会员数
    @Select("SELECT COUNT(*) FROM buy_history WHERE DATE_FORMAT(creatime,'%Y-%m-%d') = DATE_FORMAT(#{timestamp},'%Y-%m-%d')")
    Integer getLinesDay(Timestamp timestamp);

    //获取当日充值会员总金额
    @Select("SELECT SUM(price) FROM buy_history WHERE DATE_FORMAT(creatime,'%Y-%m-%d') = DATE_FORMAT(#{timestamp},'%Y-%m-%d')")
    Double getTotalPriceDay(Timestamp timestamp);

    //获取当月充值会员数
    @Select("SELECT COUNT(*) FROM buy_history WHERE DATE_FORMAT(creatime,'%Y-%m') = DATE_FORMAT(#{timestamp},'%Y-%m')")
    Integer getLinesMonth(Timestamp timestamp);

    //获取当月充值会员总金额
    @Select("SELECT SUM(price) FROM buy_history WHERE DATE_FORMAT(creatime,'%Y-%m') = DATE_FORMAT(#{timestamp},'%Y-%m')")
    Double getTotalPriceMonth(Timestamp timestamp);

    //order_id查重
    @Select("SELECT order_id FROM buy_history WHERE order_id=#{order_id}")
    String getOrder_idByOrder_id(String order_id);
}
