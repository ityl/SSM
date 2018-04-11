package com.ssm.boot.config.shiro;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by yanglei on 18/3/29.
 */
@Configuration
public class ShiorConfig {

    /**
     * shiroFilter
     *
     * @param securityManager
     * @return
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        System.out.println("ShiroConfiguration.shirFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        HashMap filters=new HashMap();
        filters.put("shiroAuthFilter","shiroAuthFilter");
        shiroFilterFactoryBean.setFilters(filters);

        //拦截器.
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/admin/**", "shiroAuthFilter[admin]");
        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/402");
        shiroFilterFactoryBean.setSuccessUrl("/");
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 自定义AuthRealm
     * @return
     */
    @Bean
    public AuthRealm shiroRealm(){
        AuthRealm authRealm = new AuthRealm();
        return authRealm;
    }

    /**
     * 安全管理器
     * @return
     */
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        //配置AuthRealm
        securityManager.setRealm(shiroRealm());
        //配置缓存管理器
        securityManager.setCacheManager(ehCacheManager());
        //配置session管理器
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    @Bean
    public FilterRegistrationBean delegatingFilterProxy(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }


    /**
     * 缓存管理器
     * @return
     */
    @Bean
    public EhCacheManager ehCacheManager(){
        System.out.println("ShiroConfiguration.getEhCacheManager()");
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:encache-shiro.xml");
        return cacheManager;
    }

    /**
     * session管理器
     * sessionManager
     */
    @Bean
    public SessionManager sessionManager(){

        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //配置session失效时长这里为20秒,过20秒后无权限继续访问
        sessionManager.setGlobalSessionTimeout(20000);
        //删除失效的session
        sessionManager.setDeleteInvalidSessions(true);
        //开启会话验证器,默认验证
        sessionManager.setSessionValidationSchedulerEnabled(true);
        return sessionManager;
    }

}