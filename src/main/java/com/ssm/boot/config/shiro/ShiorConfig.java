package com.ssm.boot.config.shiro;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.ValidatingSessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
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
    public MyShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        System.out.println("ShiroConfiguration.shirFilter()");
        MyShiroFilterFactoryBean shiroFilterFactoryBean = new MyShiroFilterFactoryBean();
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
        //开启会话验证调度器
        sessionManager.setSessionValidationScheduler(executorServiceSessionValidationScheduler());
        //shiro提供SessionDao用于会话的CRUD
        sessionManager.setSessionDAO(sessionDAO());

        //是否启用/禁用Session Id Cookie,默认是启用的
        //如果禁用后将不会设置Session Id Cookie,即默认使用了Servlet容器的JESSIONID
        //且通过URL重写(URL中的";JSESSIONID=id"部分)保存 Session Id
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionIdCookie(sessionIdCookie());
        return sessionManager;
    }


    /**
     * 回话验证调度器
     * @return
     */
    @Bean
    public ExecutorServiceSessionValidationScheduler executorServiceSessionValidationScheduler(){
        ExecutorServiceSessionValidationScheduler executorServiceSessionValidationScheduler = new ExecutorServiceSessionValidationScheduler();
        //设置会话验证调度器进行会话验证时的会话管理器
        //executorServiceSessionValidationScheduler.setSessionManager(sessionManager());
        //设置调整时间间隔,单位毫秒,默认就是1小时
        executorServiceSessionValidationScheduler.setInterval(3600000);
        return  executorServiceSessionValidationScheduler;
    }


    //会话SessionDao
    @Bean
    public EnterpriseCacheSessionDAO sessionDAO(){
        EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO();
        sessionDAO.setActiveSessionsCacheName("activeSessionsCache");
        sessionDAO.setSessionIdGenerator(sessionIdGenerator());
        return sessionDAO;
    }
    //会话ID生成器,用于生成会话ID,默认就是JavaUuidSessionIdGenerator,使用java.util.UUID生成
    @Bean
    public JavaUuidSessionIdGenerator sessionIdGenerator(){
        return new JavaUuidSessionIdGenerator();
    }

    //会话Cookie模板,sessionManager创建会话Cookie的模板
    @Bean
    public SimpleCookie sessionIdCookie(){
        SimpleCookie simpleCookie = new SimpleCookie();
        //设置cookie名字,默认JSEESSIONID
        //  <constructor-arg value="bjg_sid"/>
        //不修改使用默认的话,那么404的时候session就会过期
        simpleCookie.setName("ityl_sid");
        //如果设置为true,则客户端不会暴露给客户端脚本代码,使用HttpOnly cookie有助于减少
        //某些类型的跨站点脚本攻击
        //此特性需要实现了Servlet 2.5 MR6及以上版本的规范的Servlet容器支持
        simpleCookie.setHttpOnly(true);
        //设置cookie的过期时间,秒为单位,默认-1表示关闭浏览器是过期Cookie
        simpleCookie.setMaxAge(-1);
        //todo 只有几个项目部署同一个服务器下,使用ehcache,才能运作,不在同一个服务器就要使用redis
        //simpleCookie.setDomain(".xxx.com");
        return simpleCookie;
    }

}