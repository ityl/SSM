package com.ssm.boot.controller;


import com.ssm.boot.enums.ErrorCodeEnum;
import com.ssm.boot.exception.ServerBizException;
import com.ssm.boot.service.UserService;
import com.ssm.boot.util.ResponseUtil;
import com.ssm.boot.util.MyShiroUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by my on 2018/4/2.
 */

@Controller
@RequestMapping("/bgs")
public class LoginController {

    @Autowired
    UserService userService;



    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);


    /**
     * 登录
     * @param response
     * @param username
     * @param password
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public void login(HttpServletResponse response,
                      @RequestParam(value = "account", required = true) String username,
                      @RequestParam(value = "password", required = true) String password) {
        try {
            password=userService.serverMD5Password(password);
            MyShiroUtil.login(username,password);
            ResponseUtil.renderSuccessTipJson(response,"登录成功");
        }catch (UnknownAccountException e){
            logger.error(" exception", e);
            ResponseUtil.renderFailJson(response, ErrorCodeEnum.LOGIN_ACCOUNT_PASSWORD_ERROR);
        }catch (AuthenticationException e){
            logger.error(" exception", e);
            ResponseUtil.renderFailJson(response, ErrorCodeEnum.LOGIN_ERROR);
        } catch (Exception e) {
            ResponseUtil.renderFailJson(response, ErrorCodeEnum.SERVER_SYSERR);
            logger.error(" exception", e);
        }
    }

    /**
     * 登出
     * @param response
     */
    @RequestMapping(value = "logout")
    public void logout(HttpServletResponse response) {
        try {
            if(MyShiroUtil.logout()){
                ResponseUtil.renderSuccessTipJson(response, "退出登录");
            }else {
                ResponseUtil.renderFailJson(response,"退出失败，请重新登录");
            }
        }catch (Exception e) {
            logger.error(" exception", e);
            ResponseUtil.renderFailJson(response, ErrorCodeEnum.SERVER_SYSERR);
        }
    }

    /**
     * 修改密码
     */
    @RequestMapping(value = "resetPassword",method = RequestMethod.POST)
    public void resetPassword(HttpServletResponse response,
                              @RequestParam(required = true) String oldPassword,
                              @RequestParam(required = true) String newPassword

    ){
        try {
            userService.resetPassword(MyShiroUtil.getCurrentAccount(),oldPassword,newPassword);
            ResponseUtil.renderSuccessTipJson(response, "修改成功");
        }catch (ServerBizException e) {
            ResponseUtil.renderFailJson(response, e.getErrCode());
            logger.error(" exception", e);
        } catch (Exception e) {
            logger.error(" exception", e);
            ResponseUtil.renderFailJson(response, ErrorCodeEnum.SERVER_SYSERR);
        }
    }

    /**
     * 登陆后获取权限
     */
    @RequestMapping(value = "nav")
    public void getNav(HttpServletResponse response){
        try {
            ResponseUtil.renderSuccessJson(response,"ok",userService.getNavByAdmin(MyShiroUtil.getCurrentAccount()));
        } catch (ServerBizException e) {
            ResponseUtil.renderFailJson(response, e.getErrCode());
            logger.error(" exception", e);
        } catch (Exception e) {
            ResponseUtil.renderFailJson(response, ErrorCodeEnum.SERVER_SYSERR);
            logger.error(" exception" , e);
        }


    }
}
