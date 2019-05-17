package com.ninggc.gp.service;

import com.ninggc.gp.data.Progress;
import com.ninggc.gp.mapper.ProgressMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProgressService {

    @Resource
    private ProgressMapper progressMapper;

    public ProgressService(SqlSession session) {
        progressMapper = session.getMapper(ProgressMapper.class);
    }

    public int insert(Progress pojo){
        return progressMapper.insert(pojo);
    }

    public int insertList(List< Progress> pojos){
        return progressMapper.insertList(pojos);
    }

    public List<Progress> select(Progress pojo){
        return progressMapper.select(pojo);
    }

    public Progress selectOne(Progress pojo) {
        List<Progress> select = select(pojo);
        return (select == null || select.size() == 0) ? null : select.get(0);
    }

    public int update(Progress pojo){
        return progressMapper.update(pojo);
    }

}
