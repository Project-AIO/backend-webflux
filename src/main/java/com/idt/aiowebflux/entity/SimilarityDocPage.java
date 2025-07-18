package com.idt.aiowebflux.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_similarity_doc_page")
public class SimilarityDocPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "similarity_doc_page_id")
    private Long similarityDocPageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "similarity_doc_id", nullable = false)
    private SimilarityDoc similarityDoc;

    @Column(name = "page", nullable = false)
    private Integer page;

    @Column(name="left_x", nullable = false)
    private Float leftX;

    @Column(name="right_x", nullable = false)
    private Float rightX;

    @Column(name="top_y", nullable = false)
    private Float topY;

    @Column(name="bottom_y", nullable = false)
    private Float bottomY;

    public SimilarityDocPage(final SimilarityDoc similarityDoc, final Integer page, final Float leftX, final Float rightX, final Float topY, final Float bottomY) {
        this.similarityDoc = similarityDoc;
        this.page = page;
        this.leftX = leftX;
        this.rightX = rightX;
        this.topY = topY;
        this.bottomY = bottomY;
    }
}
