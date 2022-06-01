package com.example.musicserver.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminMapper {
    public int verifyPwd(@Param("name") String name, @Param("pwd") String pwd);
}
