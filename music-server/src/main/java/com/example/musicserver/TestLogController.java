/*
 * @Author: Axiuxiu
 * @Date: 2022-06-08 11:30:38
 * @LastEditTime: 2022-06-18 09:37:08
 * @Description: 各种测试路由
 * @Todo: 
 */
package com.example.musicserver;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.musicserver.manage.vo.RespVO;
import com.example.musicserver.utils.RespFormat;

@RestController
public class TestLogController {
    // 自定义success日志
    private static final Logger sucLog = LoggerFactory.getLogger("task_suc");
    // 自定义fail日志
    private static final Logger failLog = LoggerFactory.getLogger("task_fail");
    // 自定义当前类日志
    private static final Logger sysLog = LoggerFactory.getLogger(TestLogController.class);

    private static final String FILE_DIR = "upload";

    @GetMapping("/checkLog")
    public void check() {
        sysLog.debug("sys debug");
        sysLog.info("sys info");
        sysLog.warn("sys warn");
        sysLog.error("sys error");

        sucLog.debug("task_suc debug");
        sucLog.info("task_suc info");
        sucLog.warn("task_suc warn");
        sucLog.error("task_suc error");

        failLog.debug("task_fail debug");
        failLog.info("task_fail info");
        failLog.warn("task_fail warn");
        failLog.error("task_fail error");
    }

    static boolean createFile(String fName) {
        File dir = new File(FILE_DIR);
        boolean flag = false;
        if (!dir.exists())
            flag = dir.mkdir();
        if (!flag) {
            System.out.println("failed to create upload directory");
            return false;
        }
        File f = new File(dir, fName);
        try {
            flag = f.createNewFile();
        } catch (IOException e) {
            System.out.println("failed to create file: " + e.getMessage());
            return false;
        }
        return flag;
    }

    @PostMapping("/codeTest")
    public Map<String, Object> codeTest(@RequestBody Map<String, Object> data) {
        Map<String, Object> resp = new HashMap<>();
        resp.put(RespFormat.CODE, data.get("code"));
        Integer code = (Integer) data.get("code");
        resp.put(RespFormat.MSG, code == 200 ? "success" : "failed");
        return resp;
    }

    @PostMapping("/test")
    public RespVO test(Integer id, @RequestParam("name") String aName) {
        return new RespVO(200, "请求成功", aName + id);
    }
}
