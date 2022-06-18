package com.example.musicserver.manage.vo;

import java.time.LocalDateTime;

import com.example.musicserver.manage.entity.Song;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SongVO {
    private Integer id;

    private String name;

    private String lyrics;

    private String introduction;
    // 歌曲封面图片
    private String pic;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public SongVO(Song song) {
        this.id = song.getId();
        this.name = song.getName();
        this.lyrics = song.getLyrics();
        this.introduction = song.getIntroduction();
        this.pic = song.getPic();
        this.createTime = song.getCreateTime();
        this.updateTime = song.getUpdateTime();
    }
}
