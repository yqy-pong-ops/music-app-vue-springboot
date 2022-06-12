package com.example.musicserver.manage.service.impl;

import com.example.musicserver.manage.dto.AdminDTO;
import com.example.musicserver.manage.entity.Admin;
import com.example.musicserver.manage.mapper.AdminMapper;
import com.example.musicserver.manage.service.IAdminService;
import com.example.musicserver.manage.vo.AdminVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2022-06-06
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {
    @Override
    public Admin verifyPwd(AdminVO adminVO) throws Exception {
        Admin admin = AdminDTO.pwdDTO(adminVO);

        Admin res = getOne(new QueryWrapper<Admin>().eq("name", admin.getName()));
        if (res == null)
            return null;
        return res.getPwd().equals(admin.getPwd()) ? res : null;
    }

    @Override
    public int addAdmin(AdminVO adminVO) throws Exception {
        Admin admin = AdminDTO.pwdDTO(adminVO);

        Admin res = getOne(new QueryWrapper<Admin>().eq("name", admin.getName()));
        if (res != null)
            return 0;
        return save(admin) ? 1 : 0;
    }
}
