package com.fast.system.domain;

import java.util.HashMap;

/**
 * 操作消息提醒
 */
public class AjaxResult extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    /** 初始化一个新创建的AjaxResult对象，使其表示一个空消息 */
    public AjaxResult() {
    }

    /** 初始化一个新的AjaxResult对象 */
    public AjaxResult(int code, String msg) {
        super.put("code", code);
        super.put("msg", msg);
    }

    /** 初始化一个新的AjaxResult对象 */
    public AjaxResult(int code, String msg, Object data) {
        super.put("code", code);
        super.put("msg", msg);
        if (data != null) {
            super.put("data", data);
        }
    }

    /**
     * 返回成功消息 (String + Data)
     */
    public static AjaxResult success(String msg, Object data) {
        return new AjaxResult(200, msg, data);
    }

    /**
     * 返回成功消息 (无参)
     */
    public static AjaxResult success() {
        return AjaxResult.success("操作成功");
    }

    /**
     * 返回成功数据 (Object)
     * 修正注：你之前的代码这里调用 success("操作成功") 会导致 data 丢失
     */
    public static AjaxResult success(Object data) {
        return AjaxResult.success("操作成功", data);
    }

    /**
     * 返回成功消息 (String)
     */
    public static AjaxResult success(String msg) {
        return AjaxResult.success(msg, null);
    }

    /**
     * 返回错误消息 (String)
     */
    public static AjaxResult error(String msg) {
        return AjaxResult.error(msg, null);
    }

    /**
     * 返回错误消息 (无参)
     */
    public static AjaxResult error() {
        return AjaxResult.error("操作失败");
    }

    /**
     * 返回错误消息 (String + Data)
     */
    public static AjaxResult error(String msg, Object data) {
        return new AjaxResult(500, msg, data);
    }

    /**
     * 返回错误消息 (Code + String)
     */
    public static AjaxResult error(int code, String msg) {
        return new AjaxResult(code, msg, null);
    }

    /**
     * 方便链式调用
     */
    @Override
    public AjaxResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}