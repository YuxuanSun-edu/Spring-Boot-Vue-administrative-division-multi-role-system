package com.fast.system.config;

import com.fast.system.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * JWT拦截器：校验每个请求头中的Token
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 放行 OPTIONS 预检请求（跨域必须）
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

        // 2. 获取请求头中的 Authorization
        String token = request.getHeader("Authorization");

        try {
            if (token != null && !token.isEmpty()) {
                // 3. 校验并解析 Token
                JwtUtils.parseToken(token);
                return true; // 校验成功
            }
        } catch (Exception e) {
            // 校验失败，继续执行后面 401 逻辑
        }

        // 4. 校验失败，返回 401 状态码
        response.setStatus(401);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write("{\"code\":401,\"msg\":\"登录已过期或凭证无效\"}");
        return false;
    }
}