package com.example.musicserver.manage.dto;

import com.example.musicserver.manage.entity.Admin;
import com.example.musicserver.manage.vo.AdminVO;
import com.example.musicserver.utils.MD5Util;

public class AdminDTO {
    public static String encryptPwd(String pwd) {
        return MD5Util.md5(pwd);
    }

    public static Admin pwdDTO(AdminVO adminVO) throws Exception {
        Admin admin = (Admin) adminVO;
        String hashPwd = encryptPwd(admin.getPwd());
        if (hashPwd == null)
            throw new Exception("pwd convertion failed");
        admin.setPwd(hashPwd);
        return admin;
    }
}
