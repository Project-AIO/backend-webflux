package com.idt.aiowebflux.response;

import lombok.Getter;

@Getter
public class AiModelResponse{
    private final Long aiModelId;
    private final String name;
    private final String provider;
    private final String description;
    private final String baseUrl;
    private String apiKey;

    public AiModelResponse(final Long aiModelId, final String name, final String provider, final String description,
                          final String baseUrl) {
        this.aiModelId = aiModelId;
        this.name = name;
        this.provider = provider;
        this.description = description;
        this.baseUrl = baseUrl;
        this.apiKey ="";
    }

    public void updateApiKeyFromVault(final String apiKey){
        this.apiKey = apiKey;
    }

  /*  @QueryProjection
    public AiModelResponse(
            Long aiModelId,
            String name,
            String provider,
            Feature feature,
            Integer maxToken,
            Availability availability
    ) {
        this.aiModelId = aiModelId;
        this.name = name;
        this.provider = provider;
        this.feature = feature;
        this.maxToken = maxToken;
        this.availability = availability;
    }

    public static AiModelResponse from(final AiModel aiModel) {
        return new AiModelResponse(
                aiModel.getAiModelId(),
                aiModel.getName(),
                aiModel.getProvider(),
                aiModel.getFeature(),
                aiModel.getMaxToken(),
                aiModel.getAvailability()
        );
   }*/
}
