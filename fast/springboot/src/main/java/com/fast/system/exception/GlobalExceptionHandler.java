package com.fast.system.exception; // 也可以放在单独的 exception 包

import com.fast.system.domain.AjaxResult;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器
 * 当后端报错时，这里会拦截并返回友好的提示
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 拦截数据库主键/唯一约束冲突异常
    @ExceptionHandler(DuplicateKeyException.class)
    public AjaxResult handleDuplicateKeyException(DuplicateKeyException e) {
        return AjaxResult.error("操作失败：该数据已存在（用户名或编码重复）");
    }

    // 拦截其他数据库完整性异常
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public AjaxResult handleSQLException(SQLIntegrityConstraintViolationException e) {
        return AjaxResult.error("数据库操作失败：违反数据完整性约束");
    }

    // 拦截所有未知的运行时异常 (兜底)
    @ExceptionHandler(RuntimeException.class)
    public AjaxResult handleRuntimeException(RuntimeException e) {
        // 在控制台打印具体错误，方便开发者排查
        e.printStackTrace();
        return AjaxResult.error("系统繁忙，请稍后再试或联系管理员");
    }
}