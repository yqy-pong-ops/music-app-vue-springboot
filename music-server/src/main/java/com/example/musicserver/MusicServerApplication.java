package com.example.musicserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan({ "com.example.musicserver.manage.mapper" })
@SpringBootApplication
public class MusicServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicServerApplication.class, args);
        // System.out.println(System.getProperty("user.dir"));
    }

}
