package com.example.musicserver.manage.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RespVO {

    private Integer code;
    private String msg;
    private Object data;
}
