package com.fast.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

/**
 * 用户对象 User
 */
public class User {
    private long userId;
    private String userName;
    private Integer sex;
    private String avatar;
    private String password;
    private String role;
    private String updateBy;
    /** 修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 格式化时间，防止前端显示一串数字
    private Date updateTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    // 无参构造函数
    public User() {}

    // 全参构造函数
    public User(long userId, String userName, Integer sex, String avatar, String password, Date createTime) {
        this.userId = userId;
        this.userName = userName;
        this.sex = sex;
        this.avatar = avatar;
        this.password = password;
        this.createTime = createTime;
    }

    // --- 手动添加 Getter 和 Setter (解决“找不到符号”错误) ---

    public long getUserId() {
        return userId;
    }
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() { return updateBy; }
    public void setUpdateBy(String updateBy) { this.updateBy = updateBy; }
    public Date getUpdateTime() { return updateTime; }
    public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }
}