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
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_knowledge_config")
public class KnowledgeConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "knowledge_cfg_id")
    private Long knowledgeConfigId;

    //1:n인지 1:1인지 확인 필요
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "knowledge_id", nullable = false)
    private Knowledge knowledge;

    @Column(name = "overlap_token_r", nullable = false)
    private Float overlapTokenR;

    @Column(name = "result_cnt", nullable = false)
    private Integer resultCnt;

    @Column(name = "score_th", nullable = false)
    private Float scoreTh;

    @Column(name = "retrieval_cnt", nullable = false)
    private Integer retrievalCnt;

    @Column(name = "retrieval_weight_r", nullable = false)
    private Float retrievalWeightR;

    public KnowledgeConfig(Knowledge knowledge, Float overlapTokenR, Integer resultCnt, Float scoreTh,
                           Integer retrievalCnt,
                           Float retrievalWeightR) {
        this.knowledge = knowledge;
        this.overlapTokenR = overlapTokenR;
        this.resultCnt = resultCnt;
        this.scoreTh = scoreTh;
        this.retrievalCnt = retrievalCnt;
        this.retrievalWeightR = retrievalWeightR;
    }

    public KnowledgeConfig(final Knowledge knowledge) {
        this.knowledge = knowledge;
        this.overlapTokenR = 0.05f;
        this.resultCnt = 3;
        this.scoreTh = 50f;
        this.retrievalCnt = 50;
        this.retrievalWeightR = 0.5f;
    }

    public static KnowledgeConfig of(
            Knowledge knowledge,
            Float overlapTokenR,
            Integer resultCnt,
            Float scoreTh,
            Integer retrievalCnt,
            Float retrievalWeightR
    ) {
        return new KnowledgeConfig(
                knowledge,
                overlapTokenR,
                resultCnt,
                scoreTh,
                retrievalCnt,
                retrievalWeightR
        );

    }

    public void update(
            Float overlapTokenR,
            Integer resultCnt,
            Float scoreTh,
            Integer retrievalCnt,
            Float retrievalWeightR
    ) {
        Optional.ofNullable(overlapTokenR).ifPresent(val -> this.overlapTokenR = val);
        Optional.ofNullable(resultCnt).ifPresent(val -> this.resultCnt = val);
        Optional.ofNullable(scoreTh).ifPresent(val -> this.scoreTh = val);
        Optional.ofNullable(retrievalCnt).ifPresent(val -> this.retrievalCnt = val);
        Optional.ofNullable(retrievalWeightR).ifPresent(val -> this.retrievalWeightR = val);
    }

}
