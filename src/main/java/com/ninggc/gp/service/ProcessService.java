package com.ninggc.gp.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.ninggc.gp.data.Process;
import com.ninggc.gp.mapper.ProcessMapper;

@Service
public class ProcessService {

    @Resource
    private ProcessMapper processMapper;

    public ProcessService(SqlSession session) {
        processMapper = session.getMapper(ProcessMapper.class);
    }

    public int insert(Process pojo){
        return processMapper.insert(pojo);
    }

    public int insertList(List< Process> pojos){
        return processMapper.insertList(pojos);
    }

    public Process selectOne(Process pojo) {
        List<Process> select = select(pojo);
        return select == null ? null : select.get(0);
    }

    public List<Process> select(Process pojo){
        return processMapper.select(pojo);
    }

    public int update(Process pojo){
        return processMapper.update(pojo);
    }

}
