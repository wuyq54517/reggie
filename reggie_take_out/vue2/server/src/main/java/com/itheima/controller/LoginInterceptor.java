package com.itheima.controller;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

public class LoginInterceptor implements HandlerInterceptor {

    @Value("${interceptor.key}")
    private String key;

    private SecretKey secretKey;

    @PostConstruct
    private void init() {
        secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }

    @Value("${interceptor.mode}")
    private final InterceptorMode interceptorMode = InterceptorMode.NONE;

    @Value("${interceptor.pass}")
    private String[] interceptorPass;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (Stream.of(interceptorPass).anyMatch(p->p.equals(request.getRequestURI()))) {
            System.out.println("pass...");
            return true;
        }
        switch (interceptorMode) {
            case JWT -> {
                String token = request.getHeader("Authorization");
                if (token == null) {
                    throw new Exception401("未携带 token");
                }
                try {
                    Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
                } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException |
                         IllegalArgumentException e) {
                    throw new Exception401("校验 token 失败");
                }
            }
            case SESSION -> {
                Object user = request.getSession().getAttribute("user");
                if (user == null) {
                    throw new Exception401("校验 session 失败");
                }
            }
            case NONE -> {
            }
        }
        return true;
    }

    enum InterceptorMode {
        NONE, JWT, SESSION;
    }
}
