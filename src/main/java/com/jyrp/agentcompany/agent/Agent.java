package com.jyrp.agentcompany.agent;

import com.jyrp.agentcompany.service.ClaudeService;

public class Agent {

    private String name;
    private String role;
    private ClaudeService claudeService;

    public Agent(String name,String role, ClaudeService claudeService){
        this.name = name;
        this.role = role;
        this.claudeService = claudeService;
    }
    // 2) 생성자: 3개를 매개변수로 받아 this.필드 = 매개변수 로 저장

    // 3) work 메서드:
    public String work(String task) {
        return claudeService.askWithRole(role, task);
    }
}