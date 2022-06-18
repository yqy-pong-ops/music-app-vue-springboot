package com.example.musicserver.manage.service.impl;

import com.example.musicserver.manage.entity.Song;
import com.example.musicserver.manage.mapper.SongMapper;
import com.example.musicserver.manage.service.ISongService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2022-06-17
 */
@Service
public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements ISongService {

}
