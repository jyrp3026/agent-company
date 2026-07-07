package com.jyrp.agentcompany.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class WorkLog {

    @Id
    @GeneratedValue
    private Long id;

    private String userRequest;

    @Column(length = 5000)   // 코드가 기니까 컬럼 길이 늘려주기
    private String finalCode;

    @Column(length = 2000)
    private String finalReview;

    private int attemptCount;

    private boolean approved;

    private LocalDateTime createdAt;

    // JPA용 기본 생성자 (필수!)
    protected WorkLog() {
    }

    // 우리가 쓸 생성자
    public WorkLog(String userRequest, String finalCode, String finalReview,
                   int attemptCount, boolean approved) {
        this.userRequest = userRequest;
        this.finalCode = finalCode;
        this.finalReview = finalReview;
        this.attemptCount = attemptCount;
        this.approved = approved;
        this.createdAt = LocalDateTime.now();  // 저장 시점 시간 자동 기록
    }

    // getter들 (나중에 조회할 때 필요)
    public Long getId() { return id; }
    public String getUserRequest() { return userRequest; }
    public String getFinalCode() { return finalCode; }
    public String getFinalReview() { return finalReview; }
    public int getAttemptCount() { return attemptCount; }
    public boolean isApproved() { return approved; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}