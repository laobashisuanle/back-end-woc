package com.example.sast_pancake.controller;

import com.example.sast_pancake.common.Result;
import com.example.sast_pancake.common.Role;
import com.example.sast_pancake.common.RoleNum;
import com.example.sast_pancake.pojo.Pancake;
import com.example.sast_pancake.service.PancakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/pancake")
public class PancakeController {

   @Autowired
    private PancakeService pancakeService;

   @Autowired
   private RedisTemplate redisTemplate;


    @GetMapping
    @ResponseBody
    @RoleNum(Role.COMMON)
    public Result<List<Pancake>> getAll(){
        List<Pancake> list =new ArrayList<>();
        list = pancakeService.getAll();
        if(list.isEmpty()){
            return Result.fail(404,"未能查询到相关饼");
        }else{
            return Result.suc(list);
        }

    }
    @PostMapping
    @ResponseBody
    @RoleNum(Role.ADMIN)
    public Result insert(@RequestBody Pancake pancake){
        pancake.setCreateTime(String.valueOf(LocalDateTime.now()));
         if(pancakeService.insert(pancake)>0){
             redisTemplate.opsForValue().set(String.valueOf(pancake.getId()),pancake);
             return Result.suc(pancake);
         }else{
             return Result.fail(404,"未能成功画饼");
         }

   }
   @PutMapping("/{id}")
   @ResponseBody
   @RoleNum(Role.COMMON)
   public Result update(@PathVariable int id){
       if(pancakeService.update(id)){
           Pancake pancake =pancakeService.getById(id);

           redisTemplate.opsForValue().set(String.valueOf(id),pancake);

           return Result.suc(pancake);
       }else{
           return Result.fail(404,"未能成功做锅");
       }
   }
   @DeleteMapping("/{id}")
   @ResponseBody
   @RoleNum(Role.ADMIN)
   public Result delete(@PathVariable int id){

       if(pancakeService.delete(id)){
           redisTemplate.delete(String.valueOf(id));
           return Result.suc(null);
       }
       else{
           return Result.fail(404,"未能成功删除饼");
       }

   }
}