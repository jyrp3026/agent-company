package com.jyrp.agentcompany.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class AgentEntity {
    @Id
    @GeneratedValue
    private Long id;          // PK

    private String name;// 직원 이름 (예: "박개발")

    @Column(length = 2000)
    private String role;       // 역할 프롬프트 (예: "너는 자바 개발자야...")

    private int positionX;     // Phaser 사무실에서 책상 x좌표

    private int positionY;     // 책상 y좌표

    protected AgentEntity() {
    }

    public AgentEntity(String name, String role, int positionX, int positionY) {
        this.name = name;
        this.role = role;
        this.positionX = positionX;
        this.positionY = positionY;
    }
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getRole() { return role; }
    public int getPositionX() { return positionX; }
    public int getPositionY() { return positionY; }

}
