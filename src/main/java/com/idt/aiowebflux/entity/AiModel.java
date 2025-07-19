package com.idt.aiowebflux.entity;

import com.idt.aiowebflux.entity.constant.Feature;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_ai_model")
public class AiModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ai_model_id")
    private Long aiModelId;

    @Column(name = "name", nullable = false, length = 255, unique = true)
    private String name;

    @Size(max = 255)
    @Column(name = "provider", length = 255)
    private String provider;

    @Enumerated(EnumType.STRING)
    @Column(name = "feature", nullable = false)
    private Feature feature;

    @Column(name = "max_token")
    private Integer maxToken;

    @Column(name = "description", length = 500)
    private String description;


    @Column(name = "base_url")
    private String baseUrl;

    public AiModel(final String name, final String provider, final Feature feature, final Integer maxToken) {
        this.name = name;
        this.provider = provider;
        this.feature = feature;
        this.maxToken = maxToken;
    }


    public void update(final String provider, final Feature feature) {
        this.provider = provider;
        this.feature = feature;
    }

    public void updateBaseUrl(final String baseUrl) {
        this.baseUrl = baseUrl;
    }


    public void update(final String provider, final Feature feature, final Integer maxToken) {
        this.provider = this.provider.equals(provider) ? this.provider : provider;
        this.feature = this.feature.equals(feature) ? this.feature : feature;
        this.maxToken = this.maxToken.equals(maxToken) ? this.maxToken : maxToken;
    }
}
