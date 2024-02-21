package com.example.sast_pancake.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.sast_pancake.exception.ServiceException;
import com.example.sast_pancake.pojo.User;
import com.example.sast_pancake.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class JwtInterceptor implements HandlerInterceptor {


    //验证登录
    @Resource
    private UserService userService;

    public boolean PreHandle(HttpServletRequest request, HttpServletResponse response,Object handle){
        String token = request.getHeader("token");
        if(token.isBlank()){
            token = request.getParameter("token");
        }
        if(token.isBlank()){
            throw  new ServiceException(401,"请登录");
        }
        String userId;
        try{
            userId = JWT.decode(token).getAudience().get(0);
        }catch (JWTDecodeException e){
            throw new ServiceException(401,"请登录");
        }
        User user = userService.getById(Integer.valueOf(userId));
           if(user==null){
               throw new ServiceException(401,"请登录");
           }
          //通过密码加密后生成一个验证器
        JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256(user.getPassword())).build();
         try {
             jwtVerifier.verify(token);
         }catch (JWTVerificationException e){
             throw  new ServiceException(401,"请登录");
         }
      return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }


}
