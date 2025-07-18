package com.idt.aiowebflux.repository.custom;

import com.idt.aiowebflux.dto.ConversationPageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomConversationRepository {
    Page<ConversationPageDto> findConversationDataById(final Long conversationId, final Pageable pageable);
}
