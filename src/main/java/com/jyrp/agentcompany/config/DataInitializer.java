package com.jyrp.agentcompany.config;

import com.jyrp.agentcompany.domain.AgentEntity;
import com.jyrp.agentcompany.domain.AgentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initAgents(AgentRepository agentRepository) {
        return args -> {
            // 이미 직원이 있으면 중복 채용 방지
            if (agentRepository.count() > 0) {
                return;
            }

            agentRepository.save(new AgentEntity(
                    "손기획",
                    "너는 기획자야. 사용자의 요청을 분석해서 구체적인 요구사항 명세로 정리해. 핵심 기능 3개 이내로 간결하게.",
                    100, 150
            ));
            agentRepository.save(new AgentEntity(
                    "박개발",
                    "너는 자바 개발자 에이전트야. 요청받은 기능의 자바 코드만 간결하게 작성해. 설명은 코드 주석으로만 해.",
                    250, 150
            ));
            agentRepository.save(new AgentEntity(
                    "남리뷰",
                    "너는 시니어 코드 리뷰어 에이전트야. 주어진 자바 코드의 문제점이나 개선점을 3개 이내로 짧게 지적해. " +
                            "문제가 없으면 '승인'이라고만 답해.",
                    400, 150
            ));
            agentRepository.save(new AgentEntity(
                    "김테스터",
                    "너는 QA 테스터 에이전트야. 주어진 자바 코드에 대한 JUnit 테스트 코드를 작성해. " +
                            "정상 동작 케이스와 예외 케이스를 모두 포함해. 설명 없이 테스트 코드만 작성해.",
                    100, 300
            ));
            agentRepository.save(new AgentEntity(
                    "유팀장",
                    "너는 개발팀 팀장이야. 이 프로젝트를 확인해서 어떤걸 만들었는지 리뷰 결과는 어땠는지 잘 요약해서 "+
                    "3~5문장으로 간결하게 보고서 작성해.",
                    400, 300
            ));

            System.out.println("👥 직원 " + agentRepository.count() + "명 채용 완료!");
        };
    }
}