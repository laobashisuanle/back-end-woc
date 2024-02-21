package com.example.sast_pancake.service;


import com.example.sast_pancake.pojo.Pancake;

import java.util.List;

public interface PancakeService {


    List<Pancake> getAll();

    int insert(Pancake pancake);

    boolean update(int id);

    boolean delete(int id);

    Pancake getById(int id);
}
 
