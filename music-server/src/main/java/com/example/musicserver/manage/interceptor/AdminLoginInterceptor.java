/*
 * @Author: Axiuxiu
 * @Date: 2022-06-11 22:36:53
 * @LastEditTime: 2022-06-12 14:11:40
 * @Description: 登录验证拦截器
 * @Todo: 
 */
package com.example.musicserver.manage.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.interfaces.Claim;
import com.example.musicserver.manage.entity.Admin;
import com.example.musicserver.manage.service.IAdminService;
import com.example.musicserver.utils.JWTUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AdminLoginInterceptor implements HandlerInterceptor {
    @Autowired
    private IAdminService adminService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String token = request.getHeader("Authorization");
        if (token == null) {
            // 返回401
            response.setStatus(401);
            return false;
        }
        Map<String, Claim> payload = JWTUtil.verifyToken(token);
        if (payload == null) {
            // 返回401
            response.setStatus(401);
            return false;
        }
        Integer id = null;
        try {
            id = payload.get("id").asInt();
        } catch (Exception e) {
            log.error("Login Interceptor: " + e.getMessage());
            throw new Exception("Login Interceptor: failed to get id from token");
        }
        // System.out.println("id from token: " + id);
        Admin admin = adminService.getById(id);
        request.setAttribute("admin", admin);

        return true;
    }
}
