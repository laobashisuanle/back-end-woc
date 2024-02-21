package com.example.sast_pancake.mapper;

import com.example.sast_pancake.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDAO {

    User getByUserName(String username);

    User getById(Integer id);


    User add(User user);
}
