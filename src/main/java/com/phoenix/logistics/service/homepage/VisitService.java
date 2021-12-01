package com.phoenix.logistics.service.homepage;


/**
 * 访问次数统计服务
 */
public interface VisitService {

    //访问公司主页
    void visitCompany(long id);

    //获取当日访问次数
    int getVisitCompanyToday(long id);

}
