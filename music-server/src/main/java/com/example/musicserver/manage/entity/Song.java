package com.example.musicserver.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2022-06-17
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Song implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private Integer singerId;

    private String lyrics;

    private String introduction;
    // 歌曲封面图片
    private String pic;
    // mp3文件url
    private String url;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
