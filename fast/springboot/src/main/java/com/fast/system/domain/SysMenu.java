package com.fast.system.domain;

import lombok.Data;

@Data
public class SysMenu {
    private Long menuId;
    private String menuName; // 菜单名称
    private String perms;    // 权限标识：如 system:user:add
    private String path;     // 路由地址
    private String component;// 组件路径
}