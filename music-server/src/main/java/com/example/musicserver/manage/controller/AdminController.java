package com.example.musicserver.manage.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.example.musicserver.manage.entity.Admin;
import com.example.musicserver.manage.service.IAdminService;
import com.example.musicserver.manage.vo.AdminVO;
import com.example.musicserver.utils.JWTUtil;
import com.example.musicserver.utils.RespFormat;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2022-06-06
 */
@RestController
@RequestMapping("/manage/admin")
@Slf4j
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private HttpServletRequest request;

    @PostMapping("/login")
    /**
     * 登录操作
     * 
     * @param adminVO
     * @return TOKEN, DATA: name
     */
    public Map<String, Object> login(@RequestBody AdminVO adminVO) throws Exception {
        Map<String, Object> resp = new HashMap<>();

        Admin res = null;
        try {
            res = adminService.verifyPwd(adminVO);
        } catch (Exception e) {
            String cause = "Login Controller: " + e.getMessage();
            log.error(cause);
            throw new Exception(cause);
        }
        if (res == null) {
            resp.put(RespFormat.CODE, RespFormat.MIS_CODE);
            resp.put(RespFormat.MSG, "账号或密码错误");
            return resp;
        }

        resp.put(RespFormat.CODE, RespFormat.OK_CODE);
        resp.put(RespFormat.MSG, "登录成功");

        // 生成token并返回
        Map<String, Object> payload = new HashMap<>();
        payload.put("id", res.getId());
        String token = JWTUtil.createToken(payload);
        resp.put(RespFormat.TOKEN, token);
        // 存放data数据
        Map<String, Object> data = new HashMap<>();
        data.put("name", res.getName());
        resp.put(RespFormat.DATA, data);

        return resp;
    }

    @PostMapping("/signup")
    /**
     * 注册路由
     * 
     * @param adminVO
     * @return 返回相应信息
     */
    public Map<String, Object> signup(@RequestBody AdminVO adminVO) throws Exception {
        Map<String, Object> resp = new HashMap<>();

        int res = 0;
        try {
            res = adminService.addAdmin(adminVO);
        } catch (Exception e) {
            String cause = "Signup Controller: " + e.getMessage();
            log.error(cause);
            throw new Exception(cause);
        }

        if (res == 0) {
            resp.put(RespFormat.CODE, RespFormat.MIS_CODE);
            resp.put(RespFormat.MSG, "用户名已存在");
            return resp;
        }

        resp.put(RespFormat.CODE, RespFormat.OK_CODE);
        resp.put(RespFormat.MSG, "注册成功");
        return resp;
    }

    @GetMapping("/test")
    public Admin test() {
        return (Admin) request.getAttribute("admin");
    }
}
