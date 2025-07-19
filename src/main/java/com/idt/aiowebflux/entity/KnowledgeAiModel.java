package com.idt.aiowebflux.entity;

import com.idt.aiowebflux.entity.composite.KnowledgeAiModelId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_knowledge_ai_model")
public class KnowledgeAiModel {

    @EmbeddedId
    private KnowledgeAiModelId id;

    @MapsId("knowledgeId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "knowledge_id")
    private Knowledge knowledge;

    @MapsId("aiModelId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ai_model_id")
    private AiModel aiModel;

    //use_max_token 도 generation 모델한테만 쓰임
    @Column(name = "use_max_token")
    private Integer useMaxToken;

    public KnowledgeAiModel(final Knowledge knowledge, final AiModel aiModel) {
        this.id = new KnowledgeAiModelId(knowledge.getKnowledgeId(), aiModel.getAiModelId());
        this.knowledge = knowledge;
        this.aiModel = aiModel;
        this.useMaxToken = 4096;
    }

    public KnowledgeAiModel(final Knowledge knowledge, final AiModel aiModel, final Integer useMaxToken) {
        this.id = new KnowledgeAiModelId(knowledge.getKnowledgeId(), aiModel.getAiModelId());
        this.knowledge = knowledge;
        this.aiModel = aiModel;
        this.useMaxToken = useMaxToken;
    }

    public void updateKnowledgeAiModel(final Integer useMaxToken) {
        Optional.ofNullable(useMaxToken).ifPresent(val -> this.useMaxToken = val);
    }

//    public void updateApiKey(final String key) {
//        Optional.ofNullable(key).ifPresent(k -> this.apiKey = k);
//    }

}
