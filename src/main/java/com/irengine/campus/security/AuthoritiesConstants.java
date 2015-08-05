package com.irengine.campus.security;

/**
 * Constants for Spring Security authorities.
 */
/**
 * 角色常量
 * @author Administrator
 *
 */
public final class AuthoritiesConstants {

    private AuthoritiesConstants() {
    }
    //教务员
    public static final String ADMIN = "ROLE_ADMIN";

    //学生
    public static final String STUDENT = "ROLE_STUDENT";
    
    //教师
    public static final String TEACHER = "ROLE_TEACHER";
    
    //班主任
    public static final String ADVISER = "ROLE_ADVISER";
}
