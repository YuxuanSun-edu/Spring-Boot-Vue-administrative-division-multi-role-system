package com.fast.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class Division {
    private Long id;
    private String divCode;    // 行政区划编码
    private String divName;    // 行政区划名称
    private String manager;    // 责任人
    private String phone;      // 联系电话
    private String address;    // 办公地址
    private String createBy;   // 创建人
    private String updateBy;   // 修改人

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;   // 创建时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;   // 修改时间

    // --- Getter 和 Setter 方法 (必不可少) ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDivCode() { return divCode; }
    public void setDivCode(String divCode) { this.divCode = divCode; }
    public String getDivName() { return divName; }
    public void setDivName(String divName) { this.divName = divName; }
    public String getManager() { return manager; }
    public void setManager(String manager) { this.manager = manager; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getCreateBy() { return createBy; }
    public void setCreateBy(String createBy) { this.createBy = createBy; }
    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
    public String getUpdateBy() { return updateBy; }
    public void setUpdateBy(String updateBy) { this.updateBy = updateBy; }
    public Date getUpdateTime() { return updateTime; }
    public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }
}