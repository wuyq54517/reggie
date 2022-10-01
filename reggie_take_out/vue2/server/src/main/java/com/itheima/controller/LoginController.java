package com.itheima.controller;

import com.itheima.dto.R;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
public class LoginController {

    @Value("${interceptor.key}")
    private String key;

    private SecretKey secretKey;

    @PostConstruct
    private void init() {
        secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }


    private HttpServletRequest request;

    @Autowired
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    @RequestMapping("/api/loginJwt")
    public R loginJwt(String username, String password) {
        if (!"admin".equals(username)) {
            return R.error(401, "用户名不正确");
        }
        if (!"123".equals(password)) {
            return R.error(401, "密码不正确");
        }

        String token = Jwts.builder().signWith(secretKey).setSubject("admin").compact();
        return R.ok(Map.of("token", token));
    }

    @RequestMapping("/api/loginSession")
    public R loginSession(String username, String password, HttpSession session) {
        if (!"admin".equals(username)) {
            return R.error(401, "用户名不正确");
        }
        if (!"123".equals(password)) {
            return R.error(401, "密码不正确");
        }
        session.setAttribute("user", username);
        return R.ok(username);
    }
}
