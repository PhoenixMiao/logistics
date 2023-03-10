package com.phoenix.logistics.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.phoenix.logistics.shiro.MyRealm;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Component
public class ShiroConfig {

    @Bean
    public DefaultWebSecurityManager securityManager(MyRealm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        securityManager.setRealm(realm);
        log.info("securityManager -----------> εε§εδΊ");
        return securityManager;
    }

    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/unauth");
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauth");


        Map<String, String> hashMap = new LinkedHashMap<>();

        //swagger2
        hashMap.put("/swagger-ui.html", "anon");
        hashMap.put("/swagger/**", "anon");
        hashMap.put("/swagger-resources/**", "anon");
        hashMap.put("/v2/**", "anon");
        hashMap.put("/webjars/**", "anon");
        hashMap.put("/configuration/**", "anon");

        //account
        hashMap.put("/account/checkPassword", "anon");
        hashMap.put("/account/changPassword", "anon");
        hashMap.put("/account/changMessage", "anon");
        hashMap.put("/account/checkUsername", "anon");
        hashMap.put("/account/signIn", "anon");
        hashMap.put("/account/signOut", "anon");
        hashMap.put("/account/signUp", "anon");
        hashMap.put("/account/user/all", "roles[user]");

        //adminOrder
        hashMap.put("/admin_order/deal", "roles[admin]");
        hashMap.put("/admin_order/detail", "roles[admin]");
        hashMap.put("/admin_order/message", "roles[admin]");
        hashMap.put("/admin_order/search", "roles[admin]");
        hashMap.put("/admin_order/list", "roles[admin]");

        //car
        hashMap.put("/car/list", "roles[admin]");
        hashMap.put("/car/add", "roles[admin]");
        hashMap.put("/car/delete", "roles[admin]");
        hashMap.put("/car/free", "roles[admin]");

        //driver
        hashMap.put("/driver/list", "roles[admin]");
        hashMap.put("/driver/add", "roles[admin]");
        hashMap.put("/driver/delete", "roles[admin]");
        hashMap.put("/driver/free", "roles[admin]");

        //userOrder
        hashMap.put("/user_order/submit", "roles[user]");
        hashMap.put("/user_order/detail", "roles[user]");
        hashMap.put("/user_order/message", "roles[user]");
        hashMap.put("/user_order/search", "roles[user]");
        hashMap.put("/user_order/list", "roles[user]");
        hashMap.put("/user_order/receive", "roles[user]");


        shiroFilterFactoryBean.setFilterChainDefinitionMap(hashMap);

        return shiroFilterFactoryBean;
    }

    /**
     * εΌε―aopζ³¨θ§£ζ―ζ
     * ε³ε¨controllerδΈ­δ½Ώη¨ @RequiresPermissions("user/userList")
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor attributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        //θ?Ύη½?ε?ε¨η?‘ηε¨
        attributeSourceAdvisor.setSecurityManager(securityManager);
        return attributeSourceAdvisor;
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }




    // shiro-redis
    //====== sessionε±δΊ« ========
    /**
     * ιη½?shiro redisManager
     * δ½Ώη¨ηζ―shiro-redisεΌζΊζδ»Ά
     *
     * @return
     */
    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("127.0.0.1:6379");
        redisManager.setDatabase(0);
        return redisManager;
    }

    /**
     * DAOε€§ε?Άι½η¨θΏοΌζ°ζ?θ?Ώι?ε―Ήθ±‘οΌη¨δΊδΌθ―ηCRUDοΌζ―ε¦ζδ»¬ζ³ζSessionδΏε­ε°ζ°ζ?εΊοΌι£δΉε―δ»₯ε?η°θͺε·±ηSessionDAOοΌ
     * ιθΏε¦JDBCεε°ζ°ζ?εΊοΌζ―ε¦ζ³ζSessionζΎε°MemcachedδΈ­οΌε―δ»₯ε?η°θͺε·±ηMemcached SessionDAOοΌ
     * ε¦ε€SessionDAOδΈ­ε―δ»₯δ½Ώη¨CacheθΏθ‘ηΌε­οΌδ»₯ζι«ζ§θ½οΌ
     */
    @Bean
    RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    @Bean
    DefaultWebSessionManager sessionManager(RedisSessionDAO redisSessionDAO) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO);
        return sessionManager;
    }

    /**
     * ηΌε­ζ§εΆε¨οΌζ₯η?‘ηε¦η¨ζ·γθ§θ²γζιη­ηηΌε­ηοΌ
     * ε δΈΊθΏδΊζ°ζ?εΊζ¬δΈεΎε°ε»ζΉεοΌζΎε°ηΌε­δΈ­εε―δ»₯ζι«θ?Ώι?ηζ§θ½
     */
    @Bean
    RedisCacheManager redisCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        //redisδΈ­ιε―ΉδΈεη¨ζ·ηΌε­(ζ­€ε€ηidιθ¦ε―ΉεΊuserε?δ½δΈ­ηidε­ζ?΅,η¨δΊε―δΈζ θ―)
        redisCacheManager.setPrincipalIdFieldName("id");
        //η¨ζ·ζιδΏ‘ζ―ηΌε­ζΆι΄
        redisCacheManager.setExpire(200000);
        return redisCacheManager;
    }

}
