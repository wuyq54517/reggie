package com.itheima.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.dto.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@RestController
public class GithubController {

    @Value("${github.client_id}")
    private String clientId;

    @Value("${github.client_secret}")
    private String clientSecret;

    @Value("${github.redirect_uri}")
    private String redirectURI;

    @GetMapping("/tp/github_cb")
    public R github_cb(String code, String state) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
//        String access_token = getAccessToken(code, state, client);
        String access_token = "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";
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.github.com/user"))
                .header("Authorization", "Bearer " + access_token)
                .header("Accept", "application/json")
                .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Map<String, Object> body = new ObjectMapper().readValue(response.body(), Map.class);
        System.out.println(body);
        return R.ok(Map.of(
                "name", body.get("login"),
                "avatar", body.get("avatar_url"),
                "description", ""
        ));
    }

    private String getAccessToken(String code, String state, HttpClient client) throws IOException, InterruptedException {
        String json = new ObjectMapper().writeValueAsString(Map.of(
                "client_id", clientId,
                "client_secret", clientSecret,
                "code", code,
                "state", state
        ));
        System.out.println(json);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://github.com/login/oauth/access_token"))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        Map body = new ObjectMapper().readValue(response.body(), Map.class);
        return body.getOrDefault("access_token", "").toString();
    }
}
