package com.ninggc.gp.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.ninggc.gp.data.Role;
import com.ninggc.gp.mapper.RoleMapper;

@Service
public class RoleService {

    @Resource
    private RoleMapper roleMapper;

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

}
