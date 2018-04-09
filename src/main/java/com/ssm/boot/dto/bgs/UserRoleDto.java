package com.ssm.boot.dto.bgs;

import com.ssm.boot.dto.DbBaseObject;
import lombok.Data;

/**
 * Created by yanglei on 2018/4/2.
 */
@Data
public class UserRoleDto extends DbBaseObject<Integer> {

    /**
     * 用户id
     */
    private String u_id;

    /**
     * 权限id
     */
    private String r_id;


}
