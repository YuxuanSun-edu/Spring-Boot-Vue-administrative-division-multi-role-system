package com.fast.system.service;

import com.fast.system.domain.User;
import java.util.List;
import java.util.Set;

/**
 * 用户Service接口
 */
public interface IUserService {
    /**
     * 通过用户名查询用户
     */
    public User selectUserByUserName(String userName);

    /**
     * 查询用户列表 (这是新增的！)
     */
    public List<User> selectUserList(User user);

    /**
     * 获取用户角色集合
     */
    public Set<String> getRolePermission(Long userId);

    /**
     * 获取菜单权限集合
     */
    public Set<String> getMenuPermission(Long userId);

    public int deleteUserById(Long userId);

    public int insertUser(User user);

    public int updateUser(User user);
}