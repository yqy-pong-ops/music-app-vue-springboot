package com.example.musicserver.manage.service;

import com.example.musicserver.manage.entity.Admin;
import com.example.musicserver.manage.vo.AdminVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jobob
 * @since 2022-06-06
 */
public interface IAdminService extends IService<Admin> {
    /**
     * 验证密码
     * 
     * @param adminVO
     * @return
     * @throws Exception
     */
    public Admin verifyPwd(AdminVO adminVO) throws Exception;

    /**
     * 添加管理员：转换失败抛出异常，重名返回0
     * 
     * @param adminVO
     * @return
     * @throws Exception
     */
    public int addAdmin(AdminVO adminVO) throws Exception;
}
