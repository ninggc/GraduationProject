package com.ninggc.gp.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.ninggc.gp.data.User;
import com.ninggc.gp.data.UserMapper;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public int insert(User pojo){
        return userMapper.insert(pojo);
    }

    public int insertList(List< User> pojos){
        return userMapper.insertList(pojos);
    }

    public List<User> select(User pojo){
        return userMapper.select(pojo);
    }

    public int update(User pojo){
        return userMapper.update(pojo);
    }

}
