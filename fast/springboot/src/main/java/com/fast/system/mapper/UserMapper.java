package com.fast.system.mapper;

import com.fast.system.domain.User;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 根据用户名查询用户
     */
    public User selectUserByUserName(String userName);

    /**
     * 根据用户ID查询角色权限字符串
     */
    public List<String> selectRoleKeysByUserId(Long userId);

    /**
     * 根据用户ID查询菜单权限标识
     */
    public List<String> selectMenuPermsByUserId(Long userId);

    public List<User> selectUserList(User user);

    // 在 UserMapper.interface 中添加
    public int deleteUserById(Long userId);

    public int insertUser(User user);

    public int updateUser(User user);
}

