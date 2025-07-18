package com.idt.aiowebflux.dto;

import com.idt.aiowebflux.entity.constant.FeedbackType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter

@NoArgsConstructor
public class FeedbackDto {
    private String accountId;
    private Long questionId;
    private String questionMessage;
    private Long answerId;
    private String answerMessage;
    private FeedbackType feedbackType;
    private String feedbackComment;
    private String documentName;
    private Float similarityDocScore;

    @QueryProjection
    public FeedbackDto(String accountId, Long questionId, String questionMessage, Long answerId,
                       String answerMessage, FeedbackType feedbackType, String feedbackComment, String documentName,
                       Float similarityDocScore) {
        this.accountId = accountId;
        this.questionId = questionId;
        this.questionMessage = questionMessage;
        this.answerId = answerId;
        this.answerMessage = answerMessage;
        this.feedbackType = feedbackType;
        this.feedbackComment = feedbackComment;
        this.documentName = documentName;
        this.similarityDocScore = similarityDocScore;
    }

}
