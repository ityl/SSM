package com.ssm.boot.dto.dept;
import com.ssm.boot.dto.DbBaseObject;
import lombok.Data;

@Data
public class Dept extends DbBaseObject<String> {
    private String dept_name;
}
