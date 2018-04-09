package com.ssm.boot.dto.bgs;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.ssm.boot.dto.DbBaseObject;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by yanglei on 2018/4/2.
 */
@Data
public class UserDto extends DbBaseObject<Integer> {

    /**
     * 姓名
     */
    private String user_name;

    /**
     * 昵称
     */
    private String nick_name;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 创建人
     */
    private int create_user;

    /**
     * 创建时间
     */
    @JsonIgnore
    private Date create_time;

    /**
     * 上次登录时间
     */
    @JsonIgnore
    private Date last_login_time;

    /**
     * 上次登录ip
     */
    private String last_login_ip;

    /**
     * 更新时间
     */
    @JsonIgnore
    private Date update_time;

    private List<RoleDto> roles;


}
