package com.example.musicserver.manage.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import com.example.musicserver.manage.entity.Song;
import com.example.musicserver.manage.service.ISongService;
import com.example.musicserver.manage.vo.RespVO;
import com.example.musicserver.manage.vo.SongVO;
import com.example.musicserver.utils.RespFormat;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2022-06-17
 */
@RestController
@RequestMapping("/manage/song")
public class SongController {
    // mp3文件的本地目录
    private static final String SONG_DIR = System.getProperty("user.dir") + File.separator
            + "upload" + File.separator + "songs";
    // mp3文件上传父路径
    private static final String SONG_PATH = "/upload/songs";
    // 歌曲图片本地目录
    private static final String SONG_PIC_DIR = System.getProperty("user.dir") + File.separator + "upload"
            + File.separator + "song-pics";
    // 歌曲图片上传父路径
    private static final String SONG_PIC_PATH = "/upload/song-pics";
    // 默认歌曲图标位置
    private static final String SONG_ICON_URL = "/upload/song-icon.jpg";

    @Autowired
    private ISongService songService;

    static boolean isEmptyStr(String str) {
        return str == null || str == "";
    }

    @PostMapping("/addSong")
    /**
     * 添加歌曲
     * 
     * @param name
     * @param singerId
     * @param lyrics
     * @param introduction
     * @param mp3
     * @return
     * @throws UserNoArgExct
     * @throws LocalNoDirExct
     * @throws IllegalStateException
     * @throws IOException
     */
    public RespVO addSong(String name, Integer singerId, String lyrics, String introduction,
            @RequestParam("mp3") MultipartFile mp3)
            throws UserNoArgExct, LocalNoDirExct, IllegalStateException, IOException {
        if (isEmptyStr(name) || isEmptyStr(lyrics) || isEmptyStr(introduction) || singerId == null || mp3 == null
                || mp3.isEmpty())
            throw new UserNoArgExct("addSong: 传入参数不足！");

        String pic = SONG_ICON_URL;
        // mp3文件存入本地
        File dir = new File(SONG_DIR);
        if (!dir.exists() && !dir.mkdir())
            throw new LocalNoDirExct("服务器未创建upload文件夹");
        String filename = System.currentTimeMillis() + mp3.getOriginalFilename();
        File dest = new File(dir, filename);
        // 这里可能会抛出异常
        mp3.transferTo(dest);
        String url = SONG_PATH + "/" + filename;

        // 更新数据库
        Song song = new Song(null, name, singerId, lyrics, introduction, pic, url, LocalDateTime.now(),
                LocalDateTime.now());
        if (!songService.saveOrUpdate(song))
            return new RespVO(RespFormat.ERR_CODE, "数据库更新失败", null);

        return new RespVO(RespFormat.OK_CODE, "添加成功", null);
    }

    @GetMapping("/getSongsBySingerId")
    public RespVO getSongsBySingerId(Integer singerId) {
        List<SongVO> data = new ArrayList<>();
        List<Song> songs = songService.list(new QueryWrapper<Song>().eq("singer_id", singerId));

        for (Song it : songs) {
            data.add(new SongVO(it));
        }
        return new RespVO(RespFormat.OK_CODE, null, data);
    }

    @PostMapping("/updateSongById")
    public RespVO updateSongById(@RequestBody Song song) throws UserNoArgExct {
        if (song.getId() == null)
            throw new UserNoArgExct("缺少id字段");

        song.setSingerId(null);
        song.setCreateTime(null);
        song.setUpdateTime(LocalDateTime.now());
        if (!songService.saveOrUpdate(song))
            return new RespVO(RespFormat.ERR_CODE, "更新失败", null);

        return new RespVO(RespFormat.OK_CODE, "更新成功", null);
    }

    @PostMapping("/updatePic")
    /**
     * 更新歌曲图片：返回picUrl
     * 
     * @param id
     * @param pic
     * @return
     */
    public RespVO updatePic(Integer id, MultipartFile pic)
            throws UserNoArgExct, LocalNoDirExct, IllegalStateException, IOException {
        if (id == null)
            throw new UserNoArgExct("缺少id参数");
        if (pic == null || pic.isEmpty())
            throw new UserNoArgExct("未上传歌曲图片");

        File dir = new File(SONG_PIC_DIR);
        if (!dir.exists() && !dir.mkdir())
            throw new LocalNoDirExct("未创建upload文件夹");
        String filename = System.currentTimeMillis() + pic.getOriginalFilename();
        File dest = new File(dir, filename);
        pic.transferTo(dest);
        // 返回新的picUrl
        String picUrl = SONG_PIC_PATH + "/" + filename;
        return new RespVO(RespFormat.OK_CODE, "图片更新成功", picUrl);
    }

    @PostMapping("/deleteSongById")
    /**
     * 删除歌曲
     * 
     * @param id
     * @return
     * @throws UserNoArgExct
     */
    public RespVO deleteSongById(Integer id) throws UserNoArgExct {
        if (id == null)
            throw new UserNoArgExct("缺少id字段");
        if (!songService.removeById(id))
            return new RespVO(RespFormat.ERR_CODE, "删除失败", null);
        return new RespVO(RespFormat.OK_CODE, "删除成功", null);
    }

    @PostMapping("/deleteBatchByIds")
    /**
     * 批量删除歌曲
     * 
     * @param idList
     * @return
     * @throws UserNoArgExct
     */
    public RespVO deleteBatchByIds(@RequestBody List<Integer> idList) throws UserNoArgExct {
        if (idList == null || idList.size() == 0)
            throw new UserNoArgExct("id列表为空");
        if (!songService.removeByIds(idList))
            return new RespVO(RespFormat.ERR_CODE, "批量删除失败", null);
        return new RespVO(RespFormat.OK_CODE, "批量删除成功", null);
    }
}
