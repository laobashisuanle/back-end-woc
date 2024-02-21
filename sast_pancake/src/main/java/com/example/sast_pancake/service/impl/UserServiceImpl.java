package com.example.sast_pancake.service.impl;

import com.example.sast_pancake.exception.ServiceException;
import com.example.sast_pancake.mapper.UserDAO;
import com.example.sast_pancake.pojo.User;
import com.example.sast_pancake.service.UserService;
import com.example.sast_pancake.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public User login(User user) {
      //先去redis里面查
        User dbUser =(User)redisTemplate.opsForValue().get(user.getUsername());
       if(dbUser==null) {

           user = new User();  //空对象
           //存入空对象，解决缓存穿透
           redisTemplate.opsForValue().set(user.getUsername(), user, 5, TimeUnit.MINUTES);
           dbUser = userDAO.getByUserName(user.getUsername());
       }
        if(dbUser==null){
            throw  new ServiceException("账号或密码输入错误");
        }
        if(!user.getPassword().equals(dbUser.getPassword())){
            throw new ServiceException("账号或密码输入错误");
        }
        if(dbUser.getToken()==null){
            //设置token
            String token = TokenUtils.createToken(String.valueOf(dbUser.getId()),dbUser.getPassword());
            dbUser.setToken(token);
        }
        //如果redis里面没有dbUser数据，再添加进去
        User dbUser1 =(User)redisTemplate.opsForValue().get(dbUser.getUsername());
        if(dbUser1==null){
            redisTemplate.opsForValue().set(dbUser.getUsername(),dbUser);
        }


        return dbUser;
    }

    @Override
    public User getById(Integer id) {
        return userDAO.getById(id);
    }

    @Override
    public User register(User user) {
        User dbUser =(User)redisTemplate.opsForValue().get(user.getUsername());

        if(dbUser!=null){
            throw new ServiceException("该账号已存在");
        }

        dbUser = userDAO.getByUserName(user.getUsername());

        if(dbUser!=null){
            throw new ServiceException("该账号已存在");
        }

        userDAO.add(user);
        redisTemplate.opsForValue().set(dbUser.getUsername(),dbUser);
        return user;
    }

}
