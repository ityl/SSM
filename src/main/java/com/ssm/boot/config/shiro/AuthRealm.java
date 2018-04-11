package com.ssm.boot.config.shiro;



import com.ssm.boot.dto.bgs.RoleDto;
import com.ssm.boot.dto.bgs.UserDto;
import com.ssm.boot.exception.ServerBizException;
import com.ssm.boot.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;



@Repository
public class AuthRealm extends AuthorizingRealm {


    @Autowired
    private UserService userService;

    /**
     * 鉴权
     * @param principal
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        String loginName= SecurityUtils.getSubject().getPrincipal().toString();
        SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
        List<RoleDto> listRoles=userService.selectUserByAccount(loginName).getRoles();
        Set<String> role=new HashSet<String>();
        for (RoleDto roleDto:listRoles){
            System.out.println(roleDto.getRole());
            role.add(roleDto.getRole());
        }

        authorizationInfo.setRoles(role);
        return authorizationInfo;
    }


    /**
     * 登录认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();  //得到用户名
        String password = new String((char[]) token.getCredentials()); //得到密码

        UserDto bgsUserDto = new UserDto();
        bgsUserDto.setUser_name(username);
        bgsUserDto.setPassword(password);
        try {
            userService.login(bgsUserDto);
        } catch (ServerBizException e) {
            throw new UnknownAccountException();
        }
        AuthenticationInfo info = new SimpleAuthenticationInfo(username,password,getName());
        return info;
    }
}