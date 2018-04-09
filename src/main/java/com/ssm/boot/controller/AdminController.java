package com.ssm.boot.controller;


import com.ssm.boot.enums.ErrorCodeEnum;
import com.ssm.boot.exception.ServerBizException;
import com.ssm.boot.service.AdminService;
import com.ssm.boot.util.RequestUtil;
import com.ssm.boot.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by my on 2018/4/3.
 */
@RestController
@RequestMapping("/admin")
public class AdminController{

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    /**
     * 删除用户
     */
    @RequestMapping(value = "delete")
    public void delAdmin(HttpServletResponse response,
                         Integer uid
    ) {
        try {
            adminService.deleteByID(uid);
            ResponseUtil.renderSuccessTipJson(response, "删除成功");
        } catch (ServerBizException e) {
            ResponseUtil.renderFailJson(response, e.getErrCode());
            logger.error(" exception", e);
        } catch (Exception e) {
            ResponseUtil.renderFailJson(response, ErrorCodeEnum.SERVER_SYSERR);
            logger.error(" exception", e);
        }
    }


    /**
     * 增加用户
     *
     * @param response
     * @param request
     * @param user_name
     * @param nick_name
     * @param email
     * @param create_user
     */
    @RequestMapping(value = "add")
    public void addAdmin(HttpServletResponse response,
                         HttpServletRequest request,
                         @RequestParam(value = "account", required = true) String user_name,
                         @RequestParam(value = "nick_name", required = true) String nick_name,
                         String email,
                         int create_user,
                         String roles
    ) {
        try {
            String ip = RequestUtil.getIpAddr(request);
            adminService.addAdmin(user_name, nick_name, email, create_user, roles, ip);
            ResponseUtil.renderSuccessTipJson(response, "添加成功");
        } catch (ServerBizException e) {
            ResponseUtil.renderFailJson(response, e.getMessage());
            logger.error(" exception", e);
        } catch (Exception e) {
            ResponseUtil.renderFailJson(response, ErrorCodeEnum.SERVER_SYSERR);
            logger.error(" exception", e);
        }
    }



    /**
     *  admin 列表
     * @param response
     * @param page
     * @param size
     */
    @RequestMapping("/list")
    public void adminList(HttpServletResponse response,
                          @RequestParam(required = true) int page,
                          @RequestParam(required = true)  int size){
        try {
            ResponseUtil.renderSuccessJson(response,"ok",adminService.getList(page,size));
        }catch (Exception e) {
            ResponseUtil.renderFailJson(response, ErrorCodeEnum.SERVER_SYSERR);
            logger.error(" exception" , e);
        }

    }


    @RequestMapping(value = "/edit")
    public void editAdmin(HttpServletResponse response,
                          HttpServletRequest request,
                          Integer u_id,
                          @RequestParam(value = "account", required = true) String user_name,
                          @RequestParam(value = "nick_name", required = true) String nick_name,
                          String email,
                          int create_user,
                          String roles
    ) {
        try {
            String  ip=RequestUtil.getIpAddr(request);
            adminService.updateAdmin(u_id,user_name,nick_name,email,create_user,roles,ip);
            ResponseUtil.renderSuccessTipJson(response, "修改成功");
        } catch (ServerBizException e) {
            ResponseUtil.renderFailJson(response, e.getMessage());
            logger.error(" exception", e);
        } catch (Exception e) {
            ResponseUtil.renderFailJson(response, ErrorCodeEnum.SERVER_SYSERR);
            logger.error(" exception", e);
        }
    }

    /**
     * 角色列表
     * @param response
     */
    @RequestMapping("/roleList")
    public void roleList(HttpServletResponse response){
        try {
            ResponseUtil.renderSuccessJson(response,"ok",adminService.getRoleList());
        }catch (Exception e) {
            ResponseUtil.renderFailJson(response, ErrorCodeEnum.SERVER_SYSERR);
            logger.error(" exception" , e);
        }
    }
}
