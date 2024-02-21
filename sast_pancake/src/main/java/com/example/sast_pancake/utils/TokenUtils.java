package com.example.sast_pancake.utils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.sast_pancake.mapper.UserDAO;
import com.example.sast_pancake.pojo.User;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;

@Component
public class TokenUtils {

    @Resource
    private UserDAO userDAO;

    private static UserDAO staticUserDAO;

    public  void setUserService(){
        staticUserDAO =userDAO;
    }

   public static String createToken(String userId, String sign){

        return JWT.create().withAudience(userId)    //将userid放入token中作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(),2))   //2小时后token消失
                .sign(Algorithm.HMAC256(sign));      //这里用password做为token密钥

   }


  //根据当前token获取用户数据
  public static User getCurrentUser(){
      HttpServletRequest request =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
      try{
          String token =request.getHeader("token");
          if(!(token ==null)){
              String userId = JWT.decode(token).getAudience().get(0);
              return staticUserDAO.getById(Integer.valueOf(userId));
      }

      }catch(Exception e){
       return  null;
      }
      return null;
  }



}
