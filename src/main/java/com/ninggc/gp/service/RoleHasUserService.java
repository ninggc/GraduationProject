package com.ninggc.gp.service;

import com.ninggc.gp.mapper.RoleHasUserMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;

@Service
public class RoleHasUserService {

    @Resource(name = "roleMapper")
    private RoleHasUserMapper roleHasUserMapper;

    public RoleHasUserService(SqlSession session) {
        this.roleHasUserMapper = session.getMapper(RoleHasUserMapper.class);
    }

    public int insert(Integer role_id, String account) throws SQLIntegrityConstraintViolationException {
        return roleHasUserMapper.insert(role_id, account);
    }

//    public int insertList(List< Role> pojos){
//        return roleHasUserMapper.insertList(pojos);
//    }

    public List<Map<String, Object>> select(Integer role_id, String account){
        return roleHasUserMapper.select(role_id, account);
    }

//    public List<Role> selectWithUser(String account){
//        return roleHasUserMapper.selectWithUser(account);
//    }
//
//    public List<Role> selectWithProcess(Integer process_id){
//        return roleHasUserMapper.selectWithProcess(process_id);
//    }

    public Map<String, Object> selectOne(Integer role_id, String account){
        List<Map<String, Object>> select = roleHasUserMapper.select(role_id, account);
        return select == null || select.size() == 0 ? null : select.get(0);
    }

//    public int update(Role pojo){
//        return roleHasUserMapper.update(pojo);
//    }

    public int delete(Integer role_id, String account){
        if (role_id == null && account == null) {
            throw new RuntimeException("未指定id和account会删除所有的角色！");
        } else {
            return roleHasUserMapper.delete(role_id, account);
        }
    }

}
