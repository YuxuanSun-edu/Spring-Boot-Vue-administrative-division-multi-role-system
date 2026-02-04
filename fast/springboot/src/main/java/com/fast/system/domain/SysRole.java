package com.fast.system.domain;

import lombok.Data;

@Data
public class SysRole {
    private Long roleId;
    private String roleName; // 角色名称：如 管理员
    private String roleKey;  // 角色权限字符串：如 admin
    private String status;   // 状态：0正常, 1停用
}