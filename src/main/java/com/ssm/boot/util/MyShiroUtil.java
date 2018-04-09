package com.ssm.boot.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

/**
 * Created by yanglei on 2018/4/2.
 */

public class MyShiroUtil {

    /**
     * 登录
     * @param account
     * @param password
     */
    public static void login(String account,String password){
        UsernamePasswordToken token = new UsernamePasswordToken(account, password);
        Subject currentUser = SecurityUtils.getSubject();
        System.out.println("登录状态"+currentUser.isAuthenticated());
        currentUser.login(token);
        System.out.println("登录状态"+currentUser.isAuthenticated());

    }

    /**
     * 获取当前用户名
     * @return
     */
    public static String getCurrentAccount(){
        Subject currentUser = SecurityUtils.getSubject();
        return (String)currentUser.getPrincipal();
    }

    /**
     * 登出
     */
    public static boolean logout(){
        Subject currentUser = SecurityUtils.getSubject();
        System.out.println("登录状态"+currentUser.isAuthenticated());
        if(currentUser!=null) {
            currentUser.logout();
            System.out.println("登录状态"+currentUser.isAuthenticated());
            return true;
        }else {
            return false;
        }
    }
}
