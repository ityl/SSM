package com.ssm.boot.mapper;


import com.ssm.boot.dto.bgs.RoleDto;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by yanglei on 2018/4/4.
 */
@Repository
public class RoleMapper extends BaseMapper<RoleDto,Integer>{
    /**
     * 列表展示角色权限
     * @param u_id
     * @return
     */
    public List<RoleDto> selectRolesById(Integer u_id){
        return this.getSqlSession().selectList("selectRolesById",u_id);
    }


    public List<RoleDto> selectRoles(){
        return this.getSqlSession().selectList("selectRoles");
    }
}
