package com.phoenix.logistics.service.member.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoenix.logistics.entity.*;
import com.phoenix.logistics.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.phoenix.logistics.common.EnumExceptionType;
import com.phoenix.logistics.common.Page;
import undestiny.stell.entity.*;
import com.phoenix.logistics.exception.RRException;
import undestiny.stell.mapper.*;
import com.phoenix.logistics.service.member.MemberService;
import com.phoenix.logistics.util.DatesUtil;
import com.phoenix.logistics.util.MessageUtil;
import com.phoenix.logistics.util.RedisUtil;
import com.phoenix.logistics.util.RedisUtilComplex;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    /**
     * 收藏公司命名规则
     * key格式：{项目名}:COLLECTION:{user_id}:{company_url}
     * 用户user_id=12收藏公司company_url="abc.com"：
     * key为：STELL:COLLECTION:12:abc.com
     */

    /**
     * 收藏积分命名规则
     * key格式：{项目名}:{user_id}:COLLECTION
     * 值:{point}
     * 用户user_id=12当月收藏积分point=23.5：
     * key为：STELL:12:COLLECTION
     * 值为23.5
     */

    /**
     * 打开收藏夹中公司网址积分命名规则
     * key格式：{项目名}:{user_id}:OPENURL
     * 值:{point}
     * 用户user_id=12当月打开网址积分point=23.5：
     * key为：STELL:12:OPENURL
     * 值为23.5
     */

    /**
     * 更新报价积分命名规则
     * key格式：{项目名}:{user_id}:SETCONTENT
     * 值:{point}
     * 用户user_id=12当月更新报价积分point=23.5：
     * key为：STELL:12:SETCONTENT
     * 值为23.5
     */

    /**
     * 成功邀请好友注册并发布报价积分命名规则
     * key格式：{项目名}:{user_id}:INVITE
     * 值:{point}
     * 用户user_id=12当月邀请好友积分point=23.5：
     * key为：STELL:12:INVITE
     * 值为23.5
     */

    private static final double addCollection_point = 0.5;//单次收藏积分
    private static final double openURL_point = 0.5;//打开公司网址积分
    private static final double setContent_point = 0.5;//更新报价积分
    private static final double invite_point = 0.5;//邀请好友并发布报价积分
    private static final double recharge_point_1_month = 80;//充值一个月会员积分
    private static final double recharge_point_3_month = 300;//充值三个月会员积分
    private static final double recharge_point_6_month = 800;//充值六个月会员积分
    private static final double recharge_point_12_month = 2000;//充值一年会员积分

    private static final double addCollection_point_total_month = 50;//单次收藏积分月上限
    private static final double openURL_point_total_month = 50;//打开公司网址积分月上限
    private static final double setContent_point_total_month = 50;//更新报价积分月上限
    private static final double invite_point_total_month = 1000;//邀请好友并发布报价积分月上限

    private static final double points_to_exchange_member = 1000;//积分兑换一个月会员


    @Autowired
    RedisUtilComplex redisUtilComplex;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    Member_messageMapper member_messageMapper;

    @Autowired
    InviteMapper inviteMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    User_roleMapper user_roleMapper;

    @Override
    public Page<Member_message> getMember_messages(int user_id, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Member_message> member_messages = member_messageMapper.getMember_messagesByUser_id(user_id);
        return new Page<>(new PageInfo<>(member_messages));
    }

    @Override
    public Member getMemberInfo(int user_id) {
        Member member = Member.builder().user_id(user_id).build();
        member = memberMapper.selectOne(member);
        if(member == null){
            member = Member.builder().user_id(user_id).point(0.0).build();
            memberMapper.insert(member);
            member = memberMapper.selectOne(member);
        }
        return member;
    }

    @Override
    public Double addCollection(int user_id, String company_url) {
        if(!checkIfCompany(user_id))return 0.0;
        //用户收藏公司的当月积分的key
        String point_key = "STELL:" + user_id + ":COLLECTION";

        //用户收藏公司的key
        String collection_key = "STELL:COLLECTION:" + user_id + ":" + company_url;


        //检查该月积分是否已上限
        Double point = (Double)redisUtil.get(point_key);
        if(point == null)point = 0.0;
        if(point >= addCollection_point_total_month){
            return point;
        }

        //检查该月该公司是否已被收藏过
        if(redisUtil.get(collection_key) != null){
            return point;
        }

        //准备加分
        Date endDayOfMonth = DatesUtil.getEndDayOfMonth();

        //记录收藏公司
        redisUtilComplex.set(collection_key,1,endDayOfMonth.getTime());

        //记录收藏积分
        point = point + addCollection_point;
        redisUtilComplex.set(point_key,point,endDayOfMonth.getTime());

        //存库
        updateUserPoint(user_id,addCollection_point);

        //发送通知
        Member_message member_message = new Member_message();
        member_message.setUser_id(user_id);
        member_message.setContent(MessageUtil.addCollection(company_url,addCollection_point,point));
        member_messageMapper.insert(member_message);

        //返回加分后的当前积分
        return point;
    }



    @Override
    public Double openURL(int user_id, String company_url) {
        if(!checkIfCompany(user_id))return 0.0;
        //用户打开公司url的当月积分的key
        String point_key = "STELL:" + user_id + ":OPENURL";

        //检查该月积分是否已上限
        Double point = (Double)redisUtil.get(point_key);
        if(point == null)point = 0.0;
        if(point >= openURL_point_total_month){
            return point;
        }

        //准备加分
        Date endDayOfMonth = DatesUtil.getEndDayOfMonth();

        //记录收藏积分
        point = point + openURL_point;
        redisUtilComplex.set(point_key,point,endDayOfMonth.getTime());

        //存库
        updateUserPoint(user_id,openURL_point);

        //发送通知
        Member_message member_message = new Member_message();
        member_message.setUser_id(user_id);
        member_message.setContent(MessageUtil.openURL(company_url,openURL_point,point));
        member_messageMapper.insert(member_message);

        return point;
    }

    @Override
    public Double setContent(int user_id) {
        //用户更新报价的当月积分的key
        String point_key = "STELL:" + user_id + ":SETCONTENT";

        //检查该月积分是否已上限
        Double point = (Double)redisUtil.get(point_key);
        if(point == null)point = 0.0;
        if(point >= setContent_point_total_month){
            return point;
        }

        //准备加分
        Date endDayOfMonth = DatesUtil.getEndDayOfMonth();

        //记录收藏积分
        point = point + setContent_point;
        redisUtilComplex.set(point_key,point,endDayOfMonth.getTime());

        //存库
        updateUserPoint(user_id,setContent_point);

        //发送通知
        Member_message member_message = new Member_message();
        member_message.setUser_id(user_id);
        member_message.setContent(MessageUtil.setContent(openURL_point,point));
        member_messageMapper.insert(member_message);

        return point;
    }

    @Override
    public void inviteToSetContent(int user_id) {

        if(!checkIfCompany(user_id))return;
        int friend_id = user_id;//好友id

        Tb_user user = userMapper.getUserById(friend_id);
        String username = user.getUser_name();

        Invite invite = Invite.builder().username(username).build();
        invite = inviteMapper.selectOne(invite);

        //如果没有邀请的情况或者已经发布过报价
        if(invite == null || invite.getInviter() == null || invite.getIf_publish() == 1){
            return;
        }

        //查出邀请者
        user_id = Integer.parseInt(userMapper.getUserByUsername(invite.getInviter()).getId()+"");



        //用户推荐好友注册并第一次发布报价的当月积分的key
        String point_key = "STELL:" + user_id + ":INVITE";

        //检查该月积分是否已上限
        Double point = (Double)redisUtil.get(point_key);
        if(point == null)point = 0.0;
        if(point >= invite_point_total_month){
            return;
        }

        //准备加分
        Date endDayOfMonth = DatesUtil.getEndDayOfMonth();

        //记录积分
        point = point + invite_point;
        redisUtilComplex.set(point_key,point,endDayOfMonth.getTime());

        //存库
        updateUserPoint(user_id,invite_point);

        //发送通知
        Member_message member_message = new Member_message();
        member_message.setUser_id(user_id);
        member_message.setContent(MessageUtil.inviteToSetContent(username,invite_point,point));
        member_messageMapper.insert(member_message);

        //更新if_publish
        inviteMapper.updateIf_publishByUsername(1,username);

    }

    @Override
    public Member exchangeMember(int user_id, Integer month) {
        //找到当前用户的会员信息
        Member member = Member.builder().user_id(user_id).build();
        member = memberMapper.selectOne(member);

        //计算兑换后积分
        double point = member.getPoint() - month * points_to_exchange_member;

        //检查积分是否足够兑换
        if(point < 0){
            throw new RRException(EnumExceptionType.POINTS_NOT_ENOUGH);
        }

        //兑换
        //如果会员已经过期，就设置过期时间为从现在开始的一个月后
        //如果会员还没过期，延长一个月
        long expire_time_long = System.currentTimeMillis() + month * 2592000000L;
        Timestamp expire_time = new Timestamp(expire_time_long);
        if(member.getExpire_time() != null) {
            long tmp = member.getExpire_time().getTime() + month * 2592000000L;
            if(expire_time_long < tmp)expire_time_long = tmp;
            expire_time = new Timestamp(expire_time_long);
        }
        memberMapper.updateMemberByUser_id(point,1,expire_time,user_id);

        member.setPoint(point);
        member.setExpire_time(expire_time);
        member.setIf_member(1);

        return member;
    }

    @Override
    public Double rechargeMember(Buy_history buy_history) {
        int user_id = buy_history.getUser_id();

        if(!checkIfCompany(user_id))return 0.0;

        double point = 0.0;
        Double price = buy_history.getPrice();
        if(price == 10){
            point = recharge_point_1_month;
        }else if(price == 30){
            point  = recharge_point_3_month;
        }else if(price == 60){
            point  = recharge_point_6_month;
        }else if(price == 120){
            point  = recharge_point_12_month;
        }else{
            point = price * 10;
        }

        //存库
        double point_total = updateUserPoint(user_id, point);

        //发送通知
        Member_message member_message = new Member_message();
        member_message.setUser_id(user_id);
        member_message.setContent(MessageUtil.rechargeMember(point,point_total));
        member_messageMapper.insert(member_message);
        return point_total;
    }


    //给用户user_id加point分
    private double updateUserPoint(int user_id, Double point) {
        if(!checkIfCompany(user_id))return 0.0;
        //懒加载，先查库是否有该用户的会员信息
        Member member = Member.builder().user_id(user_id).build();
        member = memberMapper.selectOne(member);
        if(member == null){
            member = Member.builder().user_id(user_id).point(point).build();
            memberMapper.insert(member);
        }else{
            if(memberMapper.updatePointByUser_id(point + member.getPoint(),user_id) != 1){
                throw new RRException(EnumExceptionType.SYSTEM_INTERNAL_ANOMALY);
            }
        }
        return point + member.getPoint();
    }


    //检查是否是企业用户（对于一般用户没有积分的说法）
    private boolean checkIfCompany(int user_id){


        //默认都是公司，现在不区分普通用户
//        User_role user_role = user_roleMapper.getUser_roleByUser_id(user_id);
//        if(user_role == null)return false;
//        if(user_role.getCompany() == null || user_role.getCompany() != 1){
//            return false;
//        }
        return true;

    }

}
