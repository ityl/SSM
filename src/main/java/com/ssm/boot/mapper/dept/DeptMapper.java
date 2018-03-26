
package com.ssm.boot.mapper.dept;


import com.ssm.boot.dto.dept.Dept;
import com.ssm.boot.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DeptMapper extends BaseMapper<Dept, String> {
    public DeptMapper() {
    }

    public List<Dept> findList() {
        return this.getSqlSession().selectList(this.getFullSqlName("selectA"));
    }

    public Dept selectById(String id) {
        return (Dept)this.getSqlSession().selectOne(this.getFullSqlName("selectById"), id);
    }

    public int insert(Dept dept) {
        return this.getSqlSession().insert(this.getFullSqlName("insert"), dept);
    }

    public int deleteByID(String id) {
        return this.getSqlSession().delete(this.getFullSqlName("del"), id);
    }
}
