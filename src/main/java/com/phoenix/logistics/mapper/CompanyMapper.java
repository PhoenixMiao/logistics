package com.phoenix.logistics.mapper;

import com.phoenix.logistics.MyMapper;
import com.phoenix.logistics.dto.Homepage_member;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.phoenix.logistics.entity.Tb_company;

import java.sql.Timestamp;
import java.util.List;

public interface CompanyMapper extends MyMapper<Tb_company> {

    @Select("SELECT id,company_name from tb_company where company_url=#{company_url}")
    Tb_company getIdAndCompany_nameByCompany_url(String company_url);

    @Select("SELECT id,user_id,company_name,company_phone,LEFT(company_content,100) company_content,add_time,update_time,company_product,company_status,company_url from tb_company where company_url=#{company_url}")
    Homepage_member getHomepage_memberByCompany_url(String company_url);

    @Select("SELECT * FROM tb_company WHERE user_id=#{user_id}")
    Tb_company getCompanyByUser_id(Long user_id);

    @Select("SELECT id FROM tb_company WHERE user_id=#{user_id}")
    Long getIdByUser_id(Long user_id);

    @Update("UPDATE tb_company SET " +
            "company_name=#{company_name}," +
            "company_product=#{company_product}," +
            "company_intro=#{company_intro}," +
            "company_address=#{company_address}," +
            "company_contacts=#{company_contacts}," +
            "company_phone=#{company_phone}," +
            "company_mobile=#{company_mobile}," +
            "wechat=#{wechat}," +
            "qq=#{qq}," +
            "company_fax=#{company_fax}," +
            "company_url=#{company_url}" +
            " WHERE id=#{id}")
    int updateBaseInfoById(@Param("company_name")String company_name,
                           @Param("company_product")String company_product,
                           @Param("company_intro")String company_intro,
                           @Param("company_address")String company_address,
                           @Param("company_contacts")String company_contacts,
                           @Param("company_phone")String company_phone,
                           @Param("company_mobile")String company_mobile,
                           @Param("wechat")String wechat,
                           @Param("qq")String qq,
                           @Param("company_fax")String company_fax,
                           @Param("company_url")String company_url,
                           @Param("id")long id);


    @Update("UPDATE tb_company SET " +
            "company_content=#{company_content}," +
            "add_time=#{add_time}," +
            "update_time=#{update_time}," +
            "company_status=0 " +
            "WHERE id=#{id}")
    int updateContentById(@Param("company_content")String company_content,
                          @Param("add_time")Timestamp add_time,
                          @Param("update_time")Timestamp update_time,
                          @Param("id")long id);

    @Update("UPDATE tb_company SET " +
            "company_status=1 " +
            "WHERE id=#{id}")
    int closeContent(@Param("id")long id);

    @Update("UPDATE tb_company SET " +
            "company_logo=#{company_logo} " +
            "WHERE id=#{id}")
    int updateLogo(@Param("company_logo")String company_logo, @Param("id")long id);

    @Update("UPDATE tb_company SET " +
            "company_file_url=#{company_file_url} " +
            "WHERE id=#{id}")
    int updateCompany_file_url(@Param("company_file_url")String company_file_url, @Param("id")long id);



    //主页展示会员公司信息
    @Select("SELECT id,user_id,company_name,company_phone,LEFT(company_content,100) company_content,add_time,update_time,company_product,company_status,company_url,company_contacts " +
            "FROM tb_company " +
            "WHERE user_id " +
            "IN (" +
            "   SELECT user_id " +
            "   FROM member " +
            "   WHERE if_member=1) ORDER BY add_time DESC")
    List<Homepage_member> getHomepage_memberList();

    //主页展示非会员公司信息
    @Select("SELECT id,user_id,company_name,add_time,update_time,company_status,company_url " +
            "FROM tb_company " +
            "WHERE user_id " +
            "NOT IN (" +
            "   SELECT user_id " +
            "   FROM member " +
            "   WHERE if_member=1) ORDER BY add_time DESC")
    List<Homepage_member> getHomepage_notMemberList();



    //公司主页
    @Select("SELECT * FROM tb_company WHERE id=#{id}")
    Tb_company getCompanyById(long id);

    //根据公司名、主营产品、报价内容来搜索公司
    @Select("SELECT id,company_name,company_product,company_phone,LEFT(company_content,100) company_content,company_url,company_status,add_time,update_time " +
            "FROM tb_company WHERE " +
            "company_name LIKE #{keyWord_concat} or " +
            "company_product LIKE #{keyWord_concat} or " +
            "company_content LIKE #{keyWord_concat}")
    List<Tb_company> searchCompany(String keyWord_concat);

    //根据公司id查总的访问次数
    @Select("SELECT company_browse_num FROM tb_company WHERE id=#{id}")
    Integer getCompany_browse_numById(Long id);

    //根据公司id更新总的访问次数
    @Update("UPDATE tb_company SET company_browse_num = #{company_browse_num} WHERE id=#{id}")
    int setCompany_browse_numById(@Param("company_browse_num")Integer company_browse_num,@Param("id") Long id);

    //根据公司id获取用户手机号
    @Select("SELECT u.user_name FROM tb_user u,tb_company c\n" +
            "WHERE u.id=c.user_id AND c.id=#{id}")
    String getTelById(Long id);

}
