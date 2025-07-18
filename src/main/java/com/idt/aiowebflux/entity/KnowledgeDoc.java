package com.idt.aiowebflux.entity;


import com.idt.aiowebflux.entity.composite.KnowledgeDocId;
import com.idt.aiowebflux.entity.constant.State;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_knowledge_doc")
public class KnowledgeDoc {
    @EmbeddedId
    private KnowledgeDocId id;

    @MapsId("knowledgeId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "knowledge_id")
    private Knowledge knowledge;

    @MapsId("docId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_id")
    private Document doc;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private State state;

    @Column(name = "progress")
    private Integer progress;

    public KnowledgeDoc(final Knowledge knowledge, final Document doc, final State state, final Integer progress) {
        this.id = new KnowledgeDocId(knowledge.getKnowledgeId(), doc.getDocId());
        this.knowledge = knowledge;
        this.doc = doc;
        this.state = state;
        this.progress = progress;
    }
}
