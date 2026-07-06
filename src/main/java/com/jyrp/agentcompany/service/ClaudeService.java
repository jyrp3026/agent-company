package com.jyrp.agentcompany.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.List;
import java.util.Map;

@Service
public class ClaudeService {

    @Value("${anthropic.api.key}")
    private String apiKey;

    public String ask(String userMessage) {
        RestClient client = RestClient.create();

        Map<String, Object> requestBody = Map.of(
                "model", "claude-haiku-4-5-20251001",
                "max_tokens", 1024,
                "messages", List.of(
                        Map.of("role", "user", "content", userMessage)  // 빈칸 1
                )
        );

        Map response = client.post()
                .uri("https://api.anthropic.com/v1/messages")
                .header("x-api-key", apiKey)   // 빈칸 2
                .header("anthropic-version", "2023-06-01")
                .header("content-type", "application/json")
                .body(requestBody)
                .retrieve()
                .body(Map.class);

        List<Map<String, Object>> content = (List<Map<String, Object>>) response.get("content");
        return (String) content.get(0).get("text");
    }
    public String askWithRole(String role,String userMessage) {
        RestClient client = RestClient.create();

        Map<String, Object> requestBody = Map.of(
                "model", "claude-haiku-4-5-20251001",
                "max_tokens", 1024,
                "system", role,
                "messages", List.of(
                        Map.of("role", "user", "content", userMessage)  // 빈칸 1
                )
        );

        Map response = client.post()
                .uri("https://api.anthropic.com/v1/messages")
                .header("x-api-key", apiKey)   // 빈칸 2
                .header("anthropic-version", "2023-06-01")
                .header("content-type", "application/json")
                .body(requestBody)
                .retrieve()
                .body(Map.class);

        List<Map<String, Object>> content = (List<Map<String, Object>>) response.get("content");
        return (String) content.get(0).get("text");
    }

}
