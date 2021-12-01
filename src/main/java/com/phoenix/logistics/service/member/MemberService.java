package com.phoenix.logistics.service.member;

import com.phoenix.logistics.entity.Member;
import com.phoenix.logistics.entity.Member_message;
import com.phoenix.logistics.common.Page;
import com.phoenix.logistics.entity.Buy_history;


public interface MemberService {

    //获取用户积分变更消息通知
    Page<Member_message> getMember_messages(int user_id, int pageNum, int pageSize);

    //获取用户会员信息（积分等）
    Member getMemberInfo(int user_id);

    //收藏一次公司加分
    Double addCollection(int user_id,String company_url);

    //打开一次公司网址加分
    Double openURL(int user_id,String company_url);

    //更新报价加分
    Double setContent(int user_id);

    //邀请好友注册第一次发布报价加分(传的参数是好友的user_id)
    void inviteToSetContent(int user_id);

    //积分兑换会员
    Member exchangeMember(int user_id, Integer month);

    //充值会员
    Double rechargeMember(Buy_history buy_history);

}
