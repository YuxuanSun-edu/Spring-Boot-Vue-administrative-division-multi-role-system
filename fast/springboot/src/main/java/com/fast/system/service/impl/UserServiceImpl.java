package com.fast.system.service.impl;

import com.fast.system.domain.User;
import com.fast.system.mapper.UserMapper;
import com.fast.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectUserByUserName(String userName) {
        return userMapper.selectUserByUserName(userName);
    }

    @Override
    public List<User> selectUserList(User user) {
        return userMapper.selectUserList(user);
    }

    @Override
    public int insertUser(User user) {
        return userMapper.insertUser(user);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public int deleteUserById(Long userId) {
        return userMapper.deleteUserById(userId);
    }

    /**
     * ★★★ 关键修改在这里 ★★★
     * 我们不查数据库了，直接用代码写死：
     * 只要是 admin 账号登录，就给 admin 权限
     */
    @Override
    public Set<String> getRolePermission(Long userId) {
        Set<String> roles = new HashSet<>();

        // 1. 查一下这个 ID 对应的用户是谁
        // (有些简单的项目直接通过 ID 判断，比如 if(userId==1)，但如果ID变了就失效了，查名字最稳)
        // 这里为了简单，我们假设 ID 为 1 的就是管理员，或者直接返回 admin

        if (userId == 1L) {
            roles.add("admin"); // 给它管理员权限
        } else {
            roles.add("common"); // 其他人给普通权限
        }

        return roles;
    }

    @Override
    public Set<String> getMenuPermission(Long userId) {
        Set<String> perms = new HashSet<>();
        perms.add("*:*:*"); // 给所有菜单权限
        return perms;
    }
}