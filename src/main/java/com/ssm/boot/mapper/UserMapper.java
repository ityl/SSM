package com.ssm.boot.mapper;


import com.ssm.boot.dto.bgs.RoleDto;
import com.ssm.boot.dto.bgs.UserDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yanglei on 2018/4/2.
 */
@Repository
public class UserMapper extends BaseMapper<UserDto,Integer> {

    /**
     * bgs登录
     * @param userDto
     * @return
     */
    public UserDto login(UserDto userDto){
        return this.getSqlSession().selectOne("login",userDto);
    }


    /**
     *通过账号获取BGSUDto的信息
     * @param username
     * @return
     */
    public UserDto selectUserByAccount(String username){
        return  this.getSqlSession().selectOne("selectUserByAccount",username);
    }

    /**
     * 修改数据
     * @param bgsuDto
     * @return
     */
    public int updateById(UserDto bgsuDto) {
        return this.getSqlSession().update(getFullSqlName("updateById"), bgsuDto);
    }


    /**
     *  admin 列表权限展示
     * @param u_id
     * @return
     */
    public List<RoleDto> selectRolesById(String u_id){
        return this.getSqlSession().selectList("selectRolesById",u_id);
    }



    /**
     * admin 列表 total
     * @return
     */
    public String selectCount() {
        return  this.getSqlSession().selectOne("selectCount");
    }



}
