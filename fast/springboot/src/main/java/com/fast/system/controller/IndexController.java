package com.fast.system.controller;

import com.fast.system.domain.AjaxResult;
import com.fast.system.domain.User;
import com.fast.system.service.IUserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * 首页控制器，处理根路径请求
 */
@RestController //标记这个类是一个REST API控制器(自动把返回值转化为json格式)
@RequestMapping("/")
public class IndexController extends BaseController {
    @Resource
    private IUserService userService;

    @GetMapping
    public AjaxResult home(){
        return AjaxResult.success("成功启动后端") ;
    }

    /**
     * 测试接口
     */
    @GetMapping("/selectUserByUserName/{userName}")
    public AjaxResult test(@PathVariable String userName){
        User user = userService.selectUserByUserName(userName);
        return success(user) ;
    }

}
