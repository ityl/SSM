package com.ssm.boot.service;


import com.ssm.boot.dto.bgs.RoleDto;
import com.ssm.boot.dto.bgs.UserDto;
import com.ssm.boot.enums.ErrorCodeEnum;
import com.ssm.boot.exception.ServerBizException;
import com.ssm.boot.mapper.RoleMapper;
import com.ssm.boot.mapper.UserMapper;
import com.ssm.boot.mapper.UserRoleMapper;
import com.ssm.boot.util.ValidateUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;


/**
 * Created by my on 2018/4/3.
 */
@Service
public class AdminService {

    public static final Logger logger = LoggerFactory.getLogger(AdminService.class);


    @Autowired
    private UserRoleMapper uRoleMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleMapper roleMapper;

    private static final String INIT_PASSWORD = "123456";


    /**
     * 删除用户
     *
     * @param uid
     * @throws ServerBizException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteByID(Integer uid) throws ServerBizException {
        if (userMapper.deleteById(uid) == 0 || uRoleMapper.deleteById(uid) == 0) {
            throw new ServerBizException(ErrorCodeEnum.USER_DEL_ERROR);
        }
    }

    /**
     * 增加用户
     *
     * @param user_name
     * @param nick_name
     * @param email
     * @param create_user
     * @param ip
     * @throws ServerBizException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addAdmin(String user_name,
                         String nick_name,
                         String email,
                         int create_user,
                         String roles,
                         String ip) throws ServerBizException {
        if (ValidateUtil.checkEmail(email)) {
            throw new ServerBizException("邮箱验证不符合约定!");
        }
        UserDto admin = userMapper.selectUserByAccount(user_name);
        if (admin != null) {
            throw new ServerBizException("用户已经存在!");
        }
        UserDto adminDto = new UserDto();
        adminDto.setUser_name(user_name);
        adminDto.setNick_name(nick_name);
        adminDto.setEmail(email);
        adminDto.setCreate_user(create_user);
        adminDto.setPassword(userService.serverMD5Password(INIT_PASSWORD));
        adminDto.setLast_login_ip(ip);
        userMapper.insert(adminDto);
        uRoleMapper.insertRole(roles,adminDto.getId());
    }


    /**
     * 获取管理员列表
     *
     * @return
     */
    public HashMap getList(int pageSize, int size) {
        HashMap params = new HashMap();
        params.put("page", (pageSize - 1) * size);
        params.put("size", size);
        HashMap page = new HashMap();
        page.put("total", userMapper.selectCount());
        List<UserDto> adminDtos = userMapper.selectList(params);
        for (UserDto adminDto : adminDtos) {
            List<RoleDto> roleDtos = roleMapper.selectRolesById(adminDto.getId());
            adminDto.setRoles(roleDtos);
        }
        page.put("list", adminDtos);
        return page;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateAdmin(Integer u_id,
                            String user_name,
                            String nick_name,
                            String email,
                            int create_user,
                            String roles,
                            String ip)
            throws ServerBizException {
        int count = 0;
        UserDto adminDto = new UserDto();
        adminDto.setId(u_id);
        adminDto.setUser_name(user_name);
        adminDto.setNick_name(nick_name);
        adminDto.setEmail(email);
        adminDto.setCreate_user(create_user);
        adminDto.setLast_login_ip(ip);
        count = userMapper.updateById(adminDto);

        if (count == 0) {
            throw new ServerBizException("更新失败!");
        }
        if (StringUtils.isNotEmpty(roles)) {
            uRoleMapper.deleteById(Integer.valueOf(u_id));
            uRoleMapper.insertRole(roles, u_id);
        }

    }

    /**
     * 获取权限分类
     * @return
     */
    public List<RoleDto> getRoleList(){
        return  roleMapper.selectRoles();
    }

}
