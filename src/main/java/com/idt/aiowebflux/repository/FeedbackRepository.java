package com.idt.aiowebflux.repository;

import com.idt.aiowebflux.entity.Feedback;
import com.idt.aiowebflux.repository.custom.CustomFeedbackRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long>, CustomFeedbackRepository {
/*    void deleteFeedbackByAnswer_Question_Conversation_Client_ClientId(final String clientId);

    Page<Feedback> findByAnswer_Question_Conversation_Client_ClientId(final String clientId, final Pageable pageable);*/
}
