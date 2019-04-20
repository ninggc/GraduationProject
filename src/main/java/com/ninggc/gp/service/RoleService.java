package com.ninggc.gp.service;

import com.ninggc.gp.mybatis.Factory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import com.ninggc.gp.data.Role;
import com.ninggc.gp.mapper.RoleMapper;

import javax.annotation.Resource;

@Service
public class RoleService {

    @Resource(name = "roleMapper")
    private RoleMapper roleMapper;

    public RoleService(SqlSession session) throws IOException {
        this.roleMapper = session.getMapper(RoleMapper.class);
    }

    public int insert(Role pojo){
        return roleMapper.insert(pojo);
    }

    public int insertList(List< Role> pojos){
        return roleMapper.insertList(pojos);
    }

    public List<Role> select(Role pojo){
        return roleMapper.select(pojo);
    }

    public int update(Role pojo){
        return roleMapper.update(pojo);
    }

    public int delete(int id){
        return roleMapper.delete(id);
    }

}
