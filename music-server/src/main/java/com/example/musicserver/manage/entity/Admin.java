package com.example.musicserver.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2022-06-06
 */
@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String pwd;

    public Admin(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }
    
}
