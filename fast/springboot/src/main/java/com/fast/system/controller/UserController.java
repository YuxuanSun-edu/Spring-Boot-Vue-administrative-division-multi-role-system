package com.fast.system.controller;

import com.fast.system.domain.AjaxResult;
import com.fast.system.domain.User;
import com.fast.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    /**
     * 获取用户列表
     */
    @GetMapping("/list")
    public AjaxResult list(User user) {
        // startPage(); // 如果你想做分页，以后可以解开这行
        List<User> list = userService.selectUserList(user);
        return success(list);
    }

    @DeleteMapping("/{userId}")
    public AjaxResult remove(@PathVariable Long userId) {
        int rows = userService.deleteUserById(userId);
        return rows > 0 ? success() : error("删除失败");
    }

    @PostMapping
    public AjaxResult add(@RequestBody User user) {
        // 简单的后端校验
        if (user.getUserName() == null) {
            return error("用户名不能为空");
        }
        // 这里的密码目前是明文，实际项目中需要加密
        return toAjax(userService.insertUser(user));
    }

    @PutMapping
    public AjaxResult edit(@RequestBody User user) {
        // 记录是谁修改的
        user.setUpdateBy("admin");
        return toAjax(userService.updateUser(user));
    }
}