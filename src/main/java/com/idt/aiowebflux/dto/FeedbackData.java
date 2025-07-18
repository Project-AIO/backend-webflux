package com.idt.aiowebflux.dto;

import com.idt.aiowebflux.entity.Feedback;
import com.idt.aiowebflux.entity.constant.FeedbackType;

import java.time.LocalDateTime;
import java.util.List;


public record FeedbackData(
        Long feedbackId,
        Long answerId,
        FeedbackType feedbackType,
        String comment,
        LocalDateTime createdDt
) {
    public static FeedbackData from(final Feedback feedback) {
        return new FeedbackData(
                feedback.getFeedbackId(),
                feedback.getAnswer().getAnswerId(),
                feedback.getFeedbackType(),
                feedback.getComment(),
                feedback.getCreateDt());
    }

    public static List<FeedbackData> from(List<Feedback> feedbacks) {
        return feedbacks.stream()
                .map(FeedbackData::from)
                .toList();
    }
}
