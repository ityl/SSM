package com.ssm.boot.mapper;



import com.ssm.boot.dto.bgs.UserRoleDto;
import com.ssm.boot.enums.ErrorCodeEnum;
import com.ssm.boot.exception.ServerBizException;
import com.ssm.boot.util.StringUtil;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yanglei on 2018/4/4.
 */
@Repository
public class UserRoleMapper extends BaseMapper<UserRoleDto,Integer>{

    /**
     * 获取对应的角色权限
     * @param u_id
     * @return
     */
    public List<UserRoleDto> getNavByAdmin(Integer u_id){
        return this.getSqlSession().selectList(getFullSqlName("getNavByAdmin"),u_id);
    }

    /**
     * 插入数据权限角色
     * @param roles
     * @throws ServerBizException
     */
    public void insertRole(String roles,Integer u_id) throws ServerBizException {
        String[] r_ids = roles.split(",");
        for (String r_id : r_ids) {
            if(StringUtil.isMatch(r_id)){
                UserRoleDto roleDto = new UserRoleDto();
                roleDto.setU_id(u_id.toString());
                roleDto.setR_id(r_id);
                Integer count = this.getSqlSession().insert(getFullSqlName("insert"),roleDto);
                if (count <= 0) {
                    throw new ServerBizException(ErrorCodeEnum.USER_ADDROLE_ERROR);
                }
            }else {
                throw new ServerBizException(ErrorCodeEnum.USER_ADDROLE_ERROR);
            }

        }

    }

}
