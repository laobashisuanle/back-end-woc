package com.example.sast_pancake.common;

import java.lang.annotation.*;

/**
 * 0代表两种角色即可访问,1代表需要管理员角色
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RoleNum {
    int value();
}