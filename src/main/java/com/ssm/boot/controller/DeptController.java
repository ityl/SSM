
package com.ssm.boot.controller;

import com.ssm.boot.dto.dept.Dept;
import com.ssm.boot.enums.ErrorCodeEnum;
import com.ssm.boot.service.DeptService;
import com.ssm.boot.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping({"/k"})
public class DeptController {
    @Autowired
    private DeptService deptService;
    private static final Logger logger = LoggerFactory.getLogger(DeptController.class);

    public DeptController() {
    }

    @RequestMapping({"/list"})
    public void getList(HttpServletResponse response) {
        List var2 = this.deptService.getDeptList();

        try {
            ResponseUtil.renderSuccessJson(response, "ok", this.deptService.getDeptList());
        } catch (Exception e) {
            ResponseUtil.renderFailJson(response, ErrorCodeEnum.INVALID_PARAM);
            logger.error(" exception", e);
        }

    }

    @RequestMapping({"/id/{id}"})
    public void getDeptById(HttpServletResponse response, @PathVariable("id") String id) {
        try {
            ResponseUtil.renderSuccessJson(response, "ok", this.deptService.selectById(id));
        } catch (Exception e) {
            ResponseUtil.renderFailJson(response, ErrorCodeEnum.INVALID_PARAM);
            logger.error(" exception", e);
        }

    }

    @RequestMapping({"/add"})
    public void getDeptById(HttpServletResponse response, Dept dept) {
        try {
            ResponseUtil.renderSuccessJson(response, "ok", Integer.valueOf(this.deptService.insert(dept)));
        } catch (Exception e) {
            ResponseUtil.renderFailJson(response, ErrorCodeEnum.INVALID_PARAM);
            logger.error(" exception", e);
        }

    }

    @RequestMapping({"/del"})
    public void deleteByID(HttpServletResponse response, @RequestParam("id") String id) {
        try {
             ResponseUtil.renderSuccessJson(response, "ok", Integer.valueOf(this.deptService.del(id)));
        } catch (Exception e) {
            ResponseUtil.renderFailJson(response, ErrorCodeEnum.INVALID_PARAM);
            logger.error(" exception", e);
        }

    }

    @RequestMapping({"/all"})
    public void all(HttpServletResponse response) {
        try {
            ResponseUtil.renderSuccessJson(response, "ok", Integer.valueOf(this.deptService.all()));
        } catch (Exception e) {
            ResponseUtil.renderFailJson(response, ErrorCodeEnum.INVALID_PARAM);
            logger.error(" exception", e);
        }

    }
}
