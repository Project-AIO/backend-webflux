package com.idt.aiowebflux.repository;

import com.idt.aiowebflux.entity.Agent;
import com.idt.aiowebflux.repository.custom.CustomAgentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long>, CustomAgentRepository {

    void deleteByAccount_AccountIdIn(final List<String> accountIds);
}
