package com.itheima.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@RestController
public class GiteeController {

    // http://localhost:8080/tp/gitee_cb?code=xxx
    @GetMapping("/tp/gitee_cb")
    public void gitee_cb(String code, HttpServletResponse resp) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        String access_token = getAccessToken(client, code);

        Map<String, Object> body = getUserInfo(client, access_token);

        Object username = body.get("name");
        Object avatar = body.get("avatar_url");
        String token = userService.loginFromOAuth(username.toString(), avatar.toString(), "");
        System.out.println(token);
        String script = String.format("""
                                <script>
                                window.opener.postMessage('%s','%s')
                                window.close()
                                </script>
                                """, token, frontendURL);
        resp.getWriter().print(script);
    }

    @Autowired
    private UserService userService;

    @Value("${frontend.url}")
    private String frontendURL;

    @Value("${gitee.client_id}")
    private String clientId;

    @Value("${gitee.client_secret}")
    private String clientSecret;

    @Value("${gitee.redirect_uri}")
    private String redirectURI;

    private Map<String, Object> getUserInfo(HttpClient client, String access_token) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://gitee.com/api/v5/user?access_token=" + access_token)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Map<String, Object> body = new ObjectMapper().readValue(response.body(), Map.class);
        System.out.println(body);
        return body;
    }

    private String getAccessToken(HttpClient client, String code) throws IOException, InterruptedException {
        String json = new ObjectMapper().writeValueAsString(Map.of(
                "grant_type", "authorization_code",
                "client_id", clientId,
                "client_secret", clientSecret,
                "code", code,
                "redirect_uri", redirectURI
        ));
        System.out.println(json);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://gitee.com/oauth/token"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        Map body = new ObjectMapper().readValue(response.body(), Map.class);
        return body.getOrDefault("access_token", "").toString();
    }
}
