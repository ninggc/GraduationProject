package com.ninggc.gp.service;

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

    public int update(CheckUnit pojo){
        return checkUnitMapper.update(pojo);
    }

}
