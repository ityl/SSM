package com.ssm.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by my on 2018/3/22.
 */
@Controller
public class HelloController {
    @RequestMapping(value = "/")
    public String index() {
        return "/index";
    }


    @RequestMapping(value = "tologin", method = RequestMethod.GET)
    public String login() {
        return "/login";
    }
}
