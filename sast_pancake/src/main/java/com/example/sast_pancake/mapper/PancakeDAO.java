package com.example.sast_pancake.mapper;

import com.example.sast_pancake.pojo.Pancake;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PancakeDAO {

     List<Pancake> getAll();
     int insert(Pancake pancake);

     Boolean update( int id);

     Boolean delete( int id);

     Pancake getById(int id);


}
