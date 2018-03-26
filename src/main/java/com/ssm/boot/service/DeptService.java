
package com.ssm.boot.service;

import com.ssm.boot.dto.dept.Dept;
import com.ssm.boot.mapper.dept.DeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DeptService {
    @Autowired
    private DeptMapper deptMapper;

    public DeptService() {
    }

    public List<Dept> getDeptList() {
        return this.deptMapper.findList();
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = {Exception.class}
    )
    public Dept selectById(String id) {
        return this.deptMapper.selectById(id);
    }

    public int insert(Dept dept) {
        return this.deptMapper.insert(dept);
    }

    public int del(String id) {
        return this.deptMapper.deleteByID(id);
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = {Exception.class}
    )
    public int all() {
        Dept dept = new Dept();
        dept.setDept_name("谷歌");
        this.insert(dept);
        String s = null;
        ((String)s).length();
        this.del("9");
        return 1;
    }
}
