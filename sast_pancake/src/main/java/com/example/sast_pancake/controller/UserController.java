package com.example.sast_pancake.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.util.StringUtils;
import com.example.sast_pancake.common.Result;
import com.example.sast_pancake.common.Role;
import com.example.sast_pancake.common.RoleNum;
import com.example.sast_pancake.pojo.User;
import com.example.sast_pancake.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

   @Autowired
   private UserService userService;



    @PostMapping("/login")
    @ResponseBody
    @RoleNum(Role.COMMON)
    public Result login(@RequestBody User user){

        if(StrUtil.isBlank(user.getUsername()) ||StrUtil.isBlank(user.getPassword())){
            return Result.fail(404,"数据输入不合法");
        }
       user= userService.login(user);
        return Result.suc(user);
    }


    @PostMapping("/register")
    @ResponseBody
    @RoleNum(Role.COMMON)
    public Result register(@RequestBody User user){
        if(StrUtil.isBlank(user.getUsername())||StrUtil.isBlank(user.getPassword())){
            return  Result.fail(400,"账号或密码为空，输入不合法");
        }
        user=userService.register(user);
        return Result.suc(user);
    }


}
