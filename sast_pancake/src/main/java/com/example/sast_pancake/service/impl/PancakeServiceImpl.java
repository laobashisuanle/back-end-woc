package com.example.sast_pancake.service.impl;

import com.example.sast_pancake.mapper.PancakeDAO;
import com.example.sast_pancake.pojo.Pancake;
import com.example.sast_pancake.service.PancakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PancakeServiceImpl implements PancakeService {
    @Autowired
    private PancakeDAO pancakeMapper;


    @Override
    public List<Pancake> getAll() {
        return pancakeMapper.getAll();
    }

    @Override
    public int insert(Pancake pancake) {
        return pancakeMapper.insert(pancake);
    }

    @Override
    public boolean update(int id) {
        return pancakeMapper.update(id);
    }

    @Override
    public boolean delete(int id) {
        return pancakeMapper.delete(id);
    }

    @Override
    public Pancake getById(int id) {
        return pancakeMapper.getById(id);
    }
}
