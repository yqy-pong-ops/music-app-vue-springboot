package com.example.musicserver;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.musicserver.manage.entity.Admin;
import com.example.musicserver.manage.mapper.AdminMapper;
import com.example.musicserver.manage.service.IAdminService;
import com.example.musicserver.manage.vo.AdminVO;

@SpringBootTest
class MusicServerApplicationTests {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private IAdminService adminService;

    @Test
    void contextLoads() {
    }

    @Test
    /**
     * mapper接口insert测试
     */
    void adminInsertTest() {
        //
        Admin admin = new Admin("test", "testpwd");
        adminMapper.insert(admin);
        System.out.println("id: " + admin.getId());
    }

    @Test
    /**
     * mapper接口的select 测试
     */
    void adminSelectTest() {
        QueryWrapper<Admin> query = new QueryWrapper<>();
        query.between("id", 1, 4);
        List<Object> list = adminMapper.selectObjs(query);
        System.out.println("column 1 results:");
        list.forEach(System.out::println);

        System.out.println("map results");
        List<Map<String, Object>> mapRes = adminMapper.selectMaps(query);
        mapRes.forEach(System.out::println);
    }

    @Test
    /**
     * mapper接口update测试
     */
    void amdinUpdateTest() {
        UpdateWrapper<Admin> update = new UpdateWrapper<>();
        update.eq("name", "test").set("pwd", "newPwd");
        int res = adminMapper.update(null, update);
        System.out.println("update result: " + res);
    }

    @Test
    /**
     * mapper接口delete测试
     */
    void adminDeleteTest() {
        int res = adminMapper.deleteById(3);
        System.out.println("delete res: " + res);

        QueryWrapper<Admin> dWrapper = new QueryWrapper<>();
        dWrapper.eq("name", "test");
        res = adminMapper.delete(dWrapper);
        System.out.println("delete res: " + res);
    }

    @Test
    /**
     * 添加Admin 测试：重名或转换失败异常
     */
    void addAdmin() {
        AdminVO adminVO = new AdminVO();
        adminVO.setName("test");
        adminVO.setPwd("123456");
        int res = 0;
        try {
            res = adminService.addAdmin(adminVO);
        } catch (Exception e) {
            System.out.println("add admin exception: " + e.getMessage());
            return;
        }

        if (res == 0)
            System.out.println("name already exists");
        else
            System.out.println("add success");
    }
}
