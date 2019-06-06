package com.ninggc.gp.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.ninggc.gp.data.Stage;
import com.ninggc.gp.mapper.StageMapper;

@Service
public class StageService {

    @Resource
    private StageMapper stageMapper;

    public StageService(SqlSession session) {
        stageMapper = session.getMapper(StageMapper.class);
    }

    public int insert(Stage pojo){
        return stageMapper.insert(pojo);
    }

    public int insertList(List< Stage> pojos){
        return stageMapper.insertList(pojos);
    }

    public List<Stage> select(Stage pojo){
        return stageMapper.select(pojo);
    }

    public Stage selectOne(Stage pojo){
        List<Stage> select = stageMapper.select(pojo);
        return select == null || select.size() == 0 ? null : select.get(0);
    }

    public int update(Stage pojo){
        return stageMapper.update(pojo);
    }

}
