package com.jyrp.agentcompany.service;

import com.jyrp.agentcompany.agent.Agent;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    private final ClaudeService claudeService;

    public CompanyService(ClaudeService claudeService) {
        this.claudeService = claudeService;
    }

    public String runProject(String userRequest) {
        Agent developer = new Agent(
                "개발자",
                "너는 자바 개발자 에이전트야. 요청받은 기능의 자바 코드만 간결하게 작성해. 설명은 코드 주석으로만 해.",
                claudeService
        );
        Agent reviewer = new Agent(
                "리뷰어",
                "너는 시니어 코드 리뷰어 에이전트야. 주어진 자바 코드의 문제점이나 개선점을 3개 이내로 짧게 지적해. 문제가 없으면 '승인'이라고만 답해.",
                claudeService
        );
        String code = developer.work(userRequest);
        String review = reviewer.work(code);
        if(review.contains("승인")){
            return code;

        }else {
            return "코드가 반려되었습니다: " + review;
        }

        // ===== 여기부터 준이가 작성 =====
        // 1) 개발자한테 userRequest를 시켜서 결과를 code 변수에 저장
        // 2) 리뷰어한테 code를 검토시켜서 결과를 review 변수에 저장
        // 3) 만약 review에 "승인"이 포함되어 있으면 → code를 반환
        // 4) 아니면 → "코드가 반려되었습니다: " + review 를 반환 (일단 루프 없이)
        // ================================
    }
}