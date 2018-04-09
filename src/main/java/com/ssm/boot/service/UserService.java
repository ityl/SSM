package com.ssm.boot.service;



import com.ssm.boot.dto.bgs.UserDto;
import com.ssm.boot.dto.bgs.UserRoleDto;
import com.ssm.boot.enums.ErrorCodeEnum;
import com.ssm.boot.exception.ServerBizException;
import com.ssm.boot.mapper.UserRoleMapper;
import com.ssm.boot.mapper.UserMapper;
import com.ssm.boot.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by hongpengsun on 18/3/26.
 */
@Service
public class UserService {
    public static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private static final String KEY = "child_bgs";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper roleMapper;

    /**
     * 用户登录
     * @return
     */
    public UserDto login(UserDto userDto) throws ServerBizException {
        UserDto result = userMapper.login(userDto);
        if (result == null) {
            throw new ServerBizException("账号或密码错误!");
        }
        return result;
    }

    /**
     * 重置密码
     * @param account
     * @param oldPassword
     * @param newPassword
     */
    public void resetPassword( String account,
                               String oldPassword,
                               String newPassword)throws ServerBizException{
        UserDto params=new UserDto();
        params.setUser_name(account);
        params.setPassword(serverMD5Password(oldPassword));
        UserDto result = userMapper.login(params);
        if(result==null){
            throw new ServerBizException(ErrorCodeEnum.USER_OLDPASSWORD_ERROR);
        }else {
            UserDto bgsuDto = new UserDto();
            String newKey = serverMD5Password(newPassword);
            bgsuDto.setPassword(serverMD5Password(newPassword));
            logger.info("新密码"+newKey);
            bgsuDto.setId(result.getId());
            if(userMapper.updateById(bgsuDto)==0){
                throw new ServerBizException(ErrorCodeEnum.USER_CHANGE_ERROR);
            }
        }

    }
    /**
     * 鉴权通过登录名
     * @param username
     * @return
     */
    public UserDto selectUserByAccount(String username){
       return userMapper.selectUserByAccount(username);
    }

    /**
     * 后端密码加密
     *
     * @param password
     * @return
     */
    public static String serverMD5Password(String password) {
        return MD5Util.MD5Encode(MD5Util.MD5Encode(password) + KEY);
    }

    /**
     * 获取导航栏权限
     * @param account
     * @return
     */
    public HashMap getNavByAdmin(String account) throws ServerBizException{
        logger.info("用户名"+account);
        UserDto adminDto=selectUserByAccount(account);
        HashMap result=new HashMap();
        List<UserRoleDto> list;
        if(adminDto!=null) {
            result.put("name",adminDto.getUser_name());
            list = roleMapper.getNavByAdmin(adminDto.getId());
            List<String> idlists = new ArrayList<>();
            for (UserRoleDto roleDto:list){
                idlists.add(roleDto.getR_id());
            }
            result.put("roles",idlists);
            return result;
        }else {
            throw new ServerBizException(ErrorCodeEnum.USER_SESSION_EXPIRE);
        }
    }
}
