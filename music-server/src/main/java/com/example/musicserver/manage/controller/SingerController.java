package com.example.musicserver.manage.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.musicserver.excts.LocalNoDirExct;
import com.example.musicserver.excts.UserNoArgExct;
import com.example.musicserver.manage.entity.Singer;
import com.example.musicserver.manage.service.ISingerService;
import com.example.musicserver.utils.RespFormat;

import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public class SingerController {
    private static final String SINGER_PIC_DIR = System.getProperty("user.dir") + File.separator
            + "upload" + File.separator + "singer-pics";
    private static final String SINGER_PIC_PATH = "/upload/singer-pics";
    private static final String DEFAULT_PIC_URL = "/upload/singer-pics/茅原实里.jpg";

    @Autowired
    private ISingerService singerService;

    static void addDefault(Singer singer) {
        singer.setPic(DEFAULT_PIC_URL);
    }

    @PostMapping("/test")
    public Singer test(@RequestBody Singer singer) {
        return singer;
    }

    @PostMapping("/addSinger")
    /**
     * 添加singer
     * 
     * @param singer
     * @return
     */
    public Map<String, Object> addSinger(@RequestBody Singer singer) {
        Map<String, Object> resp = new HashMap<>();

        singer.setId(null);

        if (!singerService.saveOrUpdate(singer)) {
            resp.put(RespFormat.CODE, RespFormat.ERR_CODE);
            resp.put(RespFormat.MSG, "添加失败");
            return resp;
        }
        resp.put(RespFormat.CODE, RespFormat.OK_CODE);
        resp.put(RespFormat.MSG, "添加成功");
        addDefault(singer);
        resp.put(RespFormat.DATA, singer);
        return resp;
    }

    @PostMapping("/updateSinger")
    /**
     * 添加singer
     * 
     * @param singer
     * @return
     */
    public Map<String, Object> updateSinger(@RequestBody Singer singer) throws UserNoArgExct {
        Map<String, Object> resp = new HashMap<>();

        if (singer.getId() == null)
            throw new UserNoArgExct("缺少必填参数id");
        if (!singerService.saveOrUpdate(singer)) {
            resp.put(RespFormat.CODE, RespFormat.ERR_CODE);
            resp.put(RespFormat.MSG, "更新失败");
            return resp;
        }

        resp.put(RespFormat.CODE, RespFormat.OK_CODE);
        resp.put(RespFormat.MSG, "更新成功");
        resp.put(RespFormat.DATA, singer);
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
    public Map<String, Object> getByGender(@RequestBody Map<String, Object> data) throws UserNoArgExct {
        Map<String, Object> resp = new HashMap<>();

        Integer gender = (Integer) data.get("gender");
        if (gender == null)
            throw new UserNoArgExct("未传入gender字段");

        List<Singer> singers = singerService.list(new QueryWrapper<Singer>().eq("gender", gender));
        resp.put(RespFormat.CODE, RespFormat.OK_CODE);
        resp.put(RespFormat.DATA, singers);
        return resp;
    }

    @PostMapping("/updateSingerPic")
    public Map<String, Object> updateSingerPic(@RequestParam("file") MultipartFile sPic,
            @RequestParam("id") Integer id) throws UserNoArgExct, LocalNoDirExct, IllegalStateException, IOException {
        Map<String, Object> resp = new HashMap<>();
        if (sPic.isEmpty())
            throw new UserNoArgExct("上传文件为空");
        File dir = new File(SINGER_PIC_DIR);
        if (!dir.exists() && dir.mkdir())
            throw new LocalNoDirExct("未创建upload文件夹");
        String filename = sPic.getOriginalFilename() + System.currentTimeMillis();
        File dest = new File(dir, filename);
        // 这里可能会抛出IOException
        sPic.transferTo(dest);
        // 更新数据库
        String sPicUrl = SINGER_PIC_PATH + "/" + filename;
        Singer s = singerService.getById(id);
        String oldPicUrl = s.getPic();
        s.setPic(sPicUrl);
        // 可能出现数据库异常
        singerService.updateById(s);
        // 删除原有图片
        File oldPic = new File(System.getProperty("user.dir"), oldPicUrl);
        if (oldPicUrl != DEFAULT_PIC_URL && !oldPic.delete())
            log.error("failed to delete the original file");

        resp.put(RespFormat.CODE, RespFormat.OK_CODE);
        resp.put(RespFormat.DATA, sPicUrl);
        return resp;
    }

}
