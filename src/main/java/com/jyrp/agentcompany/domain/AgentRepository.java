package com.jyrp.agentcompany.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<AgentEntity, Long> {
    AgentEntity findByName(String name);
}