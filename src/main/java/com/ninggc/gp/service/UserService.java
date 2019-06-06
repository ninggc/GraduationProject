package com.ninggc.gp.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.ninggc.gp.data.User;
import com.ninggc.gp.mapper.UserMapper;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public UserService(SqlSession session) {
        userMapper = session.getMapper(UserMapper.class);
    }

    public int insert(User pojo){
        return userMapper.insert(pojo);
    }

    public int insertList(List< User> pojos){
        return userMapper.insertList(pojos);
    }

    public List<User> select(User pojo){
        return userMapper.select(pojo);
    }

    public User selectOne(User pojo){
        List<User> select = userMapper.select(pojo);
        return select == null || select.size() == 0 ? null : select.get(0);
    }

    public List<User> selectWithLimit(User pojo, int index, int size){
        return userMapper.selectWithLimit(pojo, index, size);
    }

    public int update(User pojo){
        return userMapper.update(pojo);
    }

    public int updatePassword(String account, String password){
        User pojo = new User();
        pojo.setAccount(account);
        pojo.setPass_word(password);
        return userMapper.update(pojo);
    }

    public int delete(String account){
        return userMapper.delete(account);
    }

}
