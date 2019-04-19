package com.ninggc.gp.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.ninggc.gp.data.File;
import com.ninggc.gp.mapper.FileMapper;

@Service
public class FileService {

    @Resource
    private FileMapper fileMapper;

    public int insert(File pojo){
        return fileMapper.insert(pojo);
    }

    public int insertList(List< File> pojos){
        return fileMapper.insertList(pojos);
    }

    public List<File> select(File pojo){
        return fileMapper.select(pojo);
    }

    public int update(File pojo){
        return fileMapper.update(pojo);
    }

}
