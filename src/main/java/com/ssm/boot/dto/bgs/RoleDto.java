package com.ssm.boot.dto.bgs;

import com.ssm.boot.dto.DbBaseObject;
import lombok.Data;

/**
 * Created by yanglei on 2018/4/2.
 */
@Data
public class RoleDto extends DbBaseObject<Integer> {

    /**
     * 角色
     */
    private String role;

    /**
     * 描述
     */
    private String descpt;

}
