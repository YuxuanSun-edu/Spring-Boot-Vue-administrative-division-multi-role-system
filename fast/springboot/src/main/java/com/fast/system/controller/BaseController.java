package com.fast.system.controller;

import com.fast.system.domain.AjaxResult;

/**
 * web层通用数据处理
 * 所有控制层的基类，其他控制器可继承(存放常用方法，避免每个控制器再分别写)
 */
public class BaseController {

    //返回成功(无数据)
    public AjaxResult success(){
        return AjaxResult.success();
    }

    //返回错误(无数据)
    public AjaxResult error(){
        return AjaxResult.error();
    }

    //返回成功(带消息)
    public AjaxResult success(String msg){
        return AjaxResult.success(msg);
    }

    //返回成功(带数据)
    public AjaxResult success(Object data){
        return AjaxResult.success(data);
    }

    //返回错误(带消息)
    public AjaxResult error(String msg){
        return AjaxResult.error(msg);
    }

    //根据收到影响的行数判断曹锁是否成功
    protected AjaxResult toAjax(int rows){
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

}
