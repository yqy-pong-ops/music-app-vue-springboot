package com.example.musicserver.controller;

import java.util.HashMap;
import java.util.Map;


import com.example.musicserver.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, Object> adminInfo) {
        String name = (String)adminInfo.get("name");
        String pwd = (String)adminInfo.get("pwd");
        System.out.println("name = " + name);
        System.out.println("pwd = " + pwd);
        boolean flag = adminService.verifyPwd(name, pwd);
        Map<String, Object> resp = new HashMap<>();

        if (!flag) {
            resp.put("code", 0);
            resp.put("msg", "账号或密码错误");
            return resp;
        }
        resp.put("code", 1);
        resp.put("msg", "登录成功");
        return resp;
    }
}
