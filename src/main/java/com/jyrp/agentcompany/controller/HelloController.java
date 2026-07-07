package com.jyrp.agentcompany.controller;

import com.jyrp.agentcompany.agent.Agent;
import com.jyrp.agentcompany.domain.AgentEntity;
import com.jyrp.agentcompany.domain.AgentRepository;
import com.jyrp.agentcompany.service.ClaudeService;
import com.jyrp.agentcompany.service.CompanyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HelloController {

    private final ClaudeService claudeService;
    private final CompanyService companyService;// ① 필드 선언
    private final AgentRepository agentRepository;

    public HelloController(ClaudeService claudeService, CompanyService companyService, AgentRepository agentRepository) {   // ② 생성자
        this.claudeService = claudeService;
        this.companyService = companyService;
        this.agentRepository = agentRepository;
    }

    @GetMapping("/hello")
    public String hello() {
        return "에이전트 회사 서버 가동 중";
    }

    @GetMapping("/ask")
    public String ask(@RequestParam String question) {
        return claudeService.ask(question);
    }

    @PostMapping("/dev")
    public String dev(@RequestBody String task) {
        Agent developer = new Agent(
                "개발자",
                "너는 자바 개발자 에이전트야. 요청받은 기능의 자바 코드만 간결하게 작성해. 설명은 코드 주석으로만 해.",
                claudeService
        );
        return developer.work(task);
    }

    @PostMapping("/review")
    public String review(@RequestBody String code) {
        Agent reviewer = new Agent(
                "리뷰어",
                "너는 시니어 코드 리뷰어 에이전트야. 주어진 자바 코드의 문제점이나 개선점을 3개 이내로 짧게 지적해. 문제가 없으면 '승인'이라고만 답해.",
                claudeService
        );
        return reviewer.work(code);
    }
    @PostMapping("/project")
    public String project(@RequestBody ProjectRequest request){
        return companyService.runProject(request.userRequest());
    }

    @GetMapping("/agents")
    public List<AgentEntity> agent() {
        return agentRepository.findAll();
    }

}
