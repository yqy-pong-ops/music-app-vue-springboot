package com.example.musicserver.service.impl;

import com.example.musicserver.dao.AdminMapper;
import com.example.musicserver.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public boolean verifyPwd(String name, String pwd) {
        return adminMapper.verifyPwd(name, pwd) > 0;
    }
}
