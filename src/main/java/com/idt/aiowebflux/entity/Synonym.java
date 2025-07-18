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

import java.util.Optional;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_synonym")
public class Synonym {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "synonym_id")
    private Long synonymId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Knowledge knowledge;


    @Column(name = "`source`", nullable = false, length = 25)
    private String source;


    @Column(name = "`match`", nullable = false, length = 25)
    private String match;

    public Synonym(Knowledge knowledge, String source, String match) {
        this.knowledge = knowledge;
        this.source = source;
        this.match = match;
    }

    public void update(final String source, final String match) {
        Optional.ofNullable(source).ifPresent(s -> this.source = s);
        Optional.ofNullable(match).ifPresent(m -> this.match = m);
    }

}
