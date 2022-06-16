/*
 * @Author: Axiuxiu
 * @Date: 2022-06-12 10:07:01
 * @LastEditTime: 2022-06-14 19:57:37
 * @Description: 全局异常处理器
 * @Todo: 
 */
package com.example.musicserver.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.musicserver.excts.ServerRunExct;
import com.example.musicserver.excts.UserNoArgExct;
import com.example.musicserver.utils.RespFormat;

@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(UserNoArgExct.class)
    @ResponseBody
    public Map<String, Object> argExcHandler(UserNoArgExct e){
        Map<String, Object> resp = new HashMap<>();
        resp.put(RespFormat.CODE, RespFormat.MIS_CODE);
        resp.put(RespFormat.MSG, e.getMessage());
        return resp;
    }

    @ExceptionHandler(ServerRunExct.class)
    @ResponseBody
    public Map<String, Object> runExcHandler(ServerRunExct e){
        Map<String, Object> resp = new HashMap<>();
        resp.put(RespFormat.CODE, RespFormat.ERR_CODE);
        resp.put(RespFormat.MSG, e.getMessage());
        return resp;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Map<String, Object> excHandler(Exception e) {
        Map<String, Object> resp = new HashMap<>();
        resp.put(RespFormat.CODE, 500);
        resp.put(RespFormat.MSG, e.getMessage());
        return resp;
    }
}
