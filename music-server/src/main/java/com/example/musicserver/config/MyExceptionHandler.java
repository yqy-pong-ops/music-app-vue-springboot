/*
 * @Author: Axiuxiu
 * @Date: 2022-06-12 10:07:01
 * @LastEditTime: 2022-06-12 14:07:29
 * @Description: 全局异常处理器
 * @Todo: 
 */
package com.example.musicserver.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.musicserver.utils.RespFormat;

@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Map<String, Object> excHandler(Exception e) {
        Map<String, Object> resp = new HashMap<>();
        resp.put(RespFormat.CODE, 500);
        resp.put(RespFormat.MSG, e.getMessage());
        return resp;
    }
}
