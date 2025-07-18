package com.idt.aiowebflux.repository.custom;

import com.idt.aiowebflux.dto.FeedbackDto;

import java.util.List;


public interface CustomFeedbackRepository {
    List<FeedbackDto> fetchFeedbacksByClientId(final String clientId);
}
