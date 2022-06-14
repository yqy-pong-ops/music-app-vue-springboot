package com.example.musicserver.manage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.musicserver.manage.entity.Singer;
import com.example.musicserver.manage.service.ISingerService;
import com.example.musicserver.utils.RespFormat;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2022-06-13
 */
@RestController
@RequestMapping("/manage/singer")
public class SingerController {

    @Autowired
    private ISingerService singerService;

    @PostMapping("/test")
    public Singer test(@RequestBody Singer singer) {
        return singer;
    }

    @PostMapping("/addOrUpdate")
    /**
     * 添加或修改: 传id修改，不传id添加
     * 
     * @param singer
     * @return
     */
    public Map<String, Object> addOrUpdate(@RequestBody Singer singer) {
        Map<String, Object> resp = new HashMap<>();

        boolean flag = singer.getId() == null;

        if (!singerService.saveOrUpdate(singer)) {
            resp.put(RespFormat.CODE, RespFormat.ERR_CODE);
            resp.put(RespFormat.MSG, flag ? "添加失败" : "更新失败");
            return resp;
        }

        resp.put(RespFormat.CODE, RespFormat.OK_CODE);
        resp.put(RespFormat.MSG, flag ? "添加成功" : "更新成功");
        return resp;
    }

    @PostMapping("/deleteById")
    public Map<String, Object> deleteById(@RequestBody Map<String, Object> data) {
        Map<String, Object> resp = new HashMap<>();
        Integer id = (Integer) data.get("id");
        if (id == null) {
            resp.put(RespFormat.CODE, RespFormat.MIS_CODE);
            resp.put(RespFormat.MSG, "未传入id参数");
            return resp;
        }

        if (singerService.removeById(id)) {
            resp.put(RespFormat.CODE, RespFormat.OK_CODE);
            resp.put(RespFormat.MSG, "删除成功");
        } else {
            resp.put(RespFormat.CODE, RespFormat.ERR_CODE);
            resp.put(RespFormat.MSG, "删除失败");
        }
        return resp;
    }

    @GetMapping("/getAll")
    /**
     * 查询所有歌手
     * 
     * @return 返回列表可能为空
     */
    public Map<String, Object> getAll() {
        Map<String, Object> resp = new HashMap<>();
        resp.put(RespFormat.DATA, singerService.list());
        resp.put(RespFormat.CODE, RespFormat.OK_CODE);
        return resp;
    }

    @PostMapping("/getById")
    /**
     * 根据id查询歌手
     * 
     * @param data
     * @return
     */
    public Map<String, Object> getById(@RequestBody Map<String, Object> data) {
        Map<String, Object> resp = new HashMap<>();
        Integer id = (Integer) data.get("id");
        if (id == null) {
            resp.put(RespFormat.CODE, RespFormat.MIS_CODE);
            resp.put(RespFormat.MSG, "未传入id参数");
            return resp;
        }
        Singer singer = singerService.getById(id);
        if (singer == null) {
            resp.put(RespFormat.CODE, RespFormat.MIS_CODE);
            resp.put(RespFormat.MSG, "该歌手不存在");
            return resp;
        }

        resp.put(RespFormat.CODE, RespFormat.OK_CODE);
        resp.put(RespFormat.DATA, singer);
        return resp;
    }

    @PostMapping("/singerOfName")
    /**
     * 根据name 模糊查询
     * 
     * @param data
     * @return
     */
    public Map<String, Object> singerOfName(@RequestBody Map<String, Object> data) {
        Map<String, Object> resp = new HashMap<>();
        String name = (String) data.get("name");
        if (name == null) {
            resp.put(RespFormat.CODE, RespFormat.MIS_CODE);
            resp.put(RespFormat.MSG, "未传入name参数");
            return resp;
        }
        List<Singer> singers = singerService.list(new QueryWrapper<Singer>().like("name", name));
        resp.put(RespFormat.CODE, RespFormat.OK_CODE);
        resp.put(RespFormat.DATA, singers);
        return resp;
    }

    @PostMapping("/getByGender")
    public Map<String, Object> getByGender(@RequestBody Map<String, Object> data) {
        Map<String, Object> resp = new HashMap<>();

        Boolean gender = (Boolean) data.get("gender");
        if (gender == null)
            throw new IllegalArgumentException("未传入gender字段");
            
        List<Singer> singers = singerService.list(new QueryWrapper<Singer>().eq("gender", gender));
        resp.put(RespFormat.CODE, RespFormat.OK_CODE);
        resp.put(RespFormat.DATA, singers);
        return resp;
    }
}
