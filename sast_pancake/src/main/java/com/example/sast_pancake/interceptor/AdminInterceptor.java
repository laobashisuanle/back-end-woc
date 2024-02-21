package com.example.sast_pancake.interceptor;

import com.example.sast_pancake.common.RoleNum;
import com.example.sast_pancake.pojo.User;
import com.example.sast_pancake.utils.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class AdminInterceptor implements HandlerInterceptor {


    //权限验证

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

       //根据token获取当前用户数据
        User user = TokenUtils.getCurrentUser();
        Integer type =user.getRole();
        if (hasPermission(handler, type)) {
            return true;
        } else {
            response.getWriter().write("{\"code\":403,\"msg\":\"权限不够\",\"data\":null}");
            return false;
        }

    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }


    private boolean hasPermission(Object handler, Integer type) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 获取方法上的注解
            RoleNum roleNum = handlerMethod.getMethod().getAnnotation(RoleNum.class);
            // 如果方法上的注解为空 则获取类的注解
//            if (roleNum == null) {
//                roleNum = handlerMethod.getMethod().getDeclaringClass().getAnnotation(RoleNum.class);
//            }
            // 如果标记了注解，则判断权限
            if (roleNum != null) {
                if (roleNum.value() == 0) {
                    return true;
                }
                if (roleNum.value() == type) {
                    return true;
                }
                return false;
            }
        }
        return false;

    }
}

