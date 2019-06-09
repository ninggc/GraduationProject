package com.ninggc.gp.service;

import com.ninggc.gp.data.Role;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.ninggc.gp.data.CheckUnit;
import com.ninggc.gp.mapper.CheckUnitMapper;

@Service
public class CheckUnitService {

    @Resource
    private CheckUnitMapper checkUnitMapper;

    public CheckUnitService(SqlSession session) {
        checkUnitMapper = session.getMapper(CheckUnitMapper.class);
    }

    public int insert(CheckUnit pojo){
        return checkUnitMapper.insert(pojo);
    }

    public int insertList(List< CheckUnit> pojos){
        return checkUnitMapper.insertList(pojos);
    }

    public List<CheckUnit> select(CheckUnit pojo){
        return checkUnitMapper.select(pojo);
    }

    public List<CheckUnit> selectByProcessId(int id){
        return checkUnitMapper.select(new CheckUnit().setProcess_id(id));
    }

    public List<CheckUnit> selectByStageId(int stage_id){
        return checkUnitMapper.select(new CheckUnit().setStage_id(stage_id));
    }

    public List<CheckUnit> selectByTeacherAccount(String account){
        return checkUnitMapper.selectByAccount(account);
    }

    public int update(CheckUnit pojo){
        return checkUnitMapper.update(pojo);
    }

    public int bandRole(Role pojo) {
        return checkUnitMapper.band(pojo);
    }
}
