package com.ninggc.gp.service;

import com.ninggc.gp.data.Role;
import com.ninggc.gp.util.AboutExcel.ExcelUser;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    public int insertList(List<User> pojos) throws SQLIntegrityConstraintViolationException {
        return userMapper.insertList(pojos);
    }

    public int insertListFromExcel (List<ExcelUser> pojos) throws SQLIntegrityConstraintViolationException {
        ArrayList<User> users = new ArrayList<>();
        for (ExcelUser eu : pojos) {
            User user = User.fromExcelUser(eu);
//            排除账户为空的实体
            if (user.getAccount() == null) {
                continue;
            }
            user.setUpdate_time(new Timestamp(new Date().getTime()));
            user.setVisible((byte) 1);
            users.add(user);
        }
        return userMapper.insertList(users);
    }

    public List<User> select(User pojo){
        return userMapper.select(pojo);
    }

    public int selectCount(String addition){
        return userMapper.selectCount(addition);
    }

    public User selectOne(User pojo){
        List<User> select = userMapper.select(pojo);
        return select == null || select.size() == 0 ? null : select.get(0);
    }

    /**
     *
     * @param pojo
     * @param page page从0开始计算
     * @param size
     * @return
     */
    public List<User> selectWithLimit(User pojo, int page, int size){
        return userMapper.selectWithLimit(pojo, page, size);
    }

    public List<Map<String, Object>> selectWithRole(Role role){
        return userMapper.selectWithRole(role);
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
