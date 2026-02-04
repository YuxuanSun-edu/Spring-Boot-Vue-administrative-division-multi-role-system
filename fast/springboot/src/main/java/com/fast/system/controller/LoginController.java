package com.fast.system.controller;

import com.fast.system.domain.AjaxResult;
import com.fast.system.domain.User;
import com.fast.system.service.IUserService;
import com.fast.system.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 登录验证控制器
 */
@RestController
public class LoginController extends BaseController {

    @Autowired
    private IUserService userService;

    /**
     * 登录接口
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody User loginBody) {
        // 1. 查数据库
        User user = userService.selectUserByUserName(loginBody.getUserName());

        // 2. 校验密码
        if (user == null || !user.getPassword().equals(loginBody.getPassword())) {
            return error("用户名或密码错误");
        }

        // 3. 生成 Token
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getUserId());
        claims.put("userName", user.getUserName());
        String token = JwtUtils.createToken(claims);

        // 4. 权限逻辑
        Set<String> roles = new HashSet<>();
        String dbRole = user.getRole();
        if (dbRole != null && !dbRole.isEmpty()) {
            roles.add(dbRole);
        } else {
            roles.add("common");
        }

        // 5. 封装返回
        AjaxResult ajax = success("登录成功");
        ajax.put("token", token);
        ajax.put("roles", roles);
        return ajax;
    }

    /**
     * 注册接口
     */
    @PostMapping("/register")
    public AjaxResult register(@RequestBody User user) {
        // 1. 基础参数检查
        if (user.getUserName() == null || "".equals(user.getUserName())) {
            return error("用户名不能为空");
        }
        if (user.getPassword() == null || "".equals(user.getPassword())) {
            return error("密码不能为空");
        }

        // 2. 尝试注册
        try {
            // 查重：手动检查
            User existUser = userService.selectUserByUserName(user.getUserName());
            if (existUser != null) {
                // ★★★ 改动点：直接返回原因，不要加"注册失败"前缀 ★★★
                return error("该用户名已存在，请更换！");
            }

            // 设置初始值
            user.setRole("common");
            user.setSex(0);
            user.setCreateTime(new java.util.Date());

            // 插入数据库
            int rows = userService.insertUser(user);
            return rows > 0 ? success("注册成功") : error("数据库繁忙，请稍后重试");

        } catch (Exception e) {
            // 打印报错详情，方便后端排查
            e.printStackTrace();

            // ★★★ 改动点：捕获到异常时，也只返回核心错误信息 ★★★
            // 如果是唯一索引冲突，getMessage 里会有 Duplicate entry 等字样
            return error("系统异常：" + e.getMessage());
        }
    }
}