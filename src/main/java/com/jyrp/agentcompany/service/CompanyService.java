package com.jyrp.agentcompany.service;

import com.jyrp.agentcompany.agent.Agent;
import com.jyrp.agentcompany.domain.AgentEntity;
import com.jyrp.agentcompany.domain.AgentRepository;
import com.jyrp.agentcompany.domain.WorkLog;
import com.jyrp.agentcompany.domain.WorkLogRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    private final ClaudeService claudeService;
    private final WorkLogRepository workLogRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final AgentRepository agentRepository;

    public CompanyService(ClaudeService claudeService, WorkLogRepository workLogRepository, SimpMessagingTemplate messagingTemplate,
                          AgentRepository agentRepository) {
        this.claudeService = claudeService;
        this.workLogRepository = workLogRepository;
        this.messagingTemplate = messagingTemplate;
        this.agentRepository = agentRepository;
    }

    // ★ 신규: 직원의 실제 발언을 40자로 잘라서 중계
    private void broadcast(String name, String content) {
        String preview;
        if (content.length() > 40) {
            preview = content.substring(0, 40) + "...";
        } else {
            preview = content;
        }
        // 줄바꿈이 있으면 말풍선이 이상해지니까 공백으로 교체
        preview = preview.replace("\n", " ");

        messagingTemplate.convertAndSend("/topic/office", name + ": " + preview);
    }

    public String runProject(String userRequest) {

        AgentEntity planInfo = agentRepository.findByName("손기획");
        AgentEntity devInfo = agentRepository.findByName("박개발");
        AgentEntity revInfo = agentRepository.findByName("남리뷰");
        AgentEntity testInfo = agentRepository.findByName("김테스터");
        AgentEntity mgrInfo = agentRepository.findByName("유팀장");

        Agent planner = new Agent(planInfo.getName(), planInfo.getRole(), claudeService);
        Agent developer = new Agent(devInfo.getName(), devInfo.getRole(), claudeService);
        Agent reviewer = new Agent(revInfo.getName(), revInfo.getRole(), claudeService);
        Agent tester = new Agent(testInfo.getName(), testInfo.getRole(), claudeService);
        Agent manager = new Agent(mgrInfo.getName(), mgrInfo.getRole(), claudeService);

        messagingTemplate.convertAndSend("/topic/office", "손기획: 요구사항 분석 중");
        String spec = planner.work(userRequest);
        broadcast("손기획", spec);                              // ★ 기획 결과 발언

        int attempt = 0;
        messagingTemplate.convertAndSend("/topic/office", "박개발: 코드 작성 시작");
        String code = developer.work(spec);
        broadcast("박개발", code);                              // ★ 코드 첫 부분 발언

        String review = "";
        boolean approved = false;

        while (attempt < 3) {
            messagingTemplate.convertAndSend("/topic/office", "남리뷰: 코드 검토 중");
            review = reviewer.work(code);
            broadcast("남리뷰", review);                        // ★ 리뷰 실제 내용 발언
            System.out.println("[" + (attempt + 1) + "회차] 리뷰 결과: " + review);

            if (review.contains("승인")) {
                messagingTemplate.convertAndSend("/topic/office", "리뷰어: 승인! 프로젝트 완료");
                approved = true;
                break;
            }

            attempt++;

            if (attempt < 3) {
                messagingTemplate.convertAndSend("/topic/office", "리뷰어: 반려 → 개발자: 재작성 중");
                String feedbackPrompt = userRequest + "\n\n이전 코드:\n" + code + "\n\n리뷰어 피드백:\n" + review + "\n\n위 피드백을 반영해서 코드를 다시 작성해줘.";
                code = developer.work(feedbackPrompt);
                broadcast("박개발", code);                      // ★ 재작성 코드도 발언
            }
        }
        workLogRepository.save(new WorkLog(userRequest, code, review, attempt + 1, approved));
        if (!approved) {
            messagingTemplate.convertAndSend("/topic/office", "프로젝트 실패 (3회 반려)");
        }

        String testCode = "";
        if (approved) {
            messagingTemplate.convertAndSend("/topic/office", "김테스터: 테스트 코드 작성 중");
            testCode = tester.work(code);
            broadcast("김테스터", testCode);                    // ★ 테스트 코드 발언
        }
        messagingTemplate.convertAndSend("/topic/office", "유팀장: 최종 보고서 작성 중");
        String reportInput = "요청: " + userRequest
                + "\n\n최종 코드:\n" + code
                + "\n\n리뷰 결과: " + review
                + "\n\n시도 횟수: " + (attempt + 1) + "회"
                + "\n\n테스트 코드:\n" + testCode;
        String report = manager.work(reportInput);
        broadcast("유팀장", report);                            // ★ 보고서 첫 부분 발언

        return report;
    }
}