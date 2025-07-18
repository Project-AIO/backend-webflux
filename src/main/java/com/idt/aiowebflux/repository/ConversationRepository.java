package com.idt.aiowebflux.repository;

import com.idt.aiowebflux.entity.Conversation;
import com.idt.aiowebflux.repository.custom.CustomConversationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long>, CustomConversationRepository {
  //  Optional<Conversation> findTopByClient_ClientIdAndKnowledge_KnowledgeIdOrderByCreateDtDesc(final String clientId, final Long knowledgeId);

/*    @Modifying
    @Query("DELETE FROM Conversation c WHERE c.client.clientId = :clientId AND c.knowledge.knowledgeId = :knowledgeId")
    void deleteConversationsByClient_ClientIdAndKnowledge_KnowledgeId(final String clientId, final Long knowledgeId);*/

    void deleteByAccount_AccountIdIn(final  List<String> accountIds);
}
