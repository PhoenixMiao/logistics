package com.phoenix.logistics.service.account;

public interface UpgradeAuthService {

    //普通用户提升为企业账号
    void upgradeAuth(String username);

}
