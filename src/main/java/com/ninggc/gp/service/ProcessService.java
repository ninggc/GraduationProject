package com.ninggc.gp.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.ninggc.gp.data.Process;
import com.ninggc.gp.data.ProcessMapper;

@Service
public class ProcessService {

    @Resource
    private ProcessMapper processMapper;

    public int insert(Process pojo){
        return processMapper.insert(pojo);
    }

    public int insertList(List< Process> pojos){
        return processMapper.insertList(pojos);
    }

    public List<Process> select(Process pojo){
        return processMapper.select(pojo);
    }

    public int update(Process pojo){
        return processMapper.update(pojo);
    }

}
