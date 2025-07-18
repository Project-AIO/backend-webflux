package com.idt.aiowebflux.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_answer")
public class Answer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long answerId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", referencedColumnName = "question_id", nullable = false)
    private Question question;

    @Column(name = "message")
    private String message;

    @Column(name = "pipeline_log")
    private String pipelineLog;

    @Column(name = "done_dt", updatable = false)
    private LocalDateTime doneDt;

    @Override
    protected void prePersistChild() {
        this.doneDt = LocalDateTime.now();
    }

    public Answer(Question question, String message, String pipelineLog) {
        this.question = question;
        this.message = message;
        this.pipelineLog = pipelineLog;
    }

    public void updateMessage(final String message, final String pipelineLog) {
        this.message = message;
        this.pipelineLog = pipelineLog;
    }
}
