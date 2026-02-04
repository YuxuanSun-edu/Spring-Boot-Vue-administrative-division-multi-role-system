package com.fast.system.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * JWT 工具类，负责生成和解析令牌
 */
@Component
public class JwtUtils {
    // 密钥，生产环境建议写在 yml 配置文件中
    private static final String secret = "fast_springboot_vue_secret_key_666";
    // 有效期：24小时 (单位：毫秒)
    private static final long expireTime = 24 * 60 * 60 * 1000;

    /**
     * 根据用户信息生成令牌
     */
    public static String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 解析令牌，获取其中存储的信息
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}