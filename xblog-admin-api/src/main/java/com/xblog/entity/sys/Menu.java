package com.xblog.entity.sys;

import com.xblog.commons.validator.annotation.Max;
import com.xblog.commons.validator.annotation.Min;
import com.xblog.commons.validator.annotation.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * 菜单
 */
@Entity
@Table
@Data
public class Menu {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id; // id

    @Column(length = 128)
    private String title; // 菜单标题

    @Column
    private String description; // 菜单描述

    @Column
    @Min(value = 0, message = "parentId不能小于0")
    private Integer parentId; // 父id

    @Column
    private long createTime; // 创建时间

    @Column
    @Min(1)
    @Max(3)
    private Integer role; // 菜单角色

    @Column(length = 64)
    @NotNull
    private String urlPath; // 菜单路径

    // 数据库忽略该字段
    @Transient
    private List<Menu> children;
}
