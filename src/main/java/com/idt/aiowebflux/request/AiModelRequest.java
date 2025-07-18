package com.idt.aiowebflux.request;

import com.idt.aiowebflux.entity.constant.Feature;

public record AiModelRequest(
        String name,
        String provider,
        Feature feature,
        String apiKey,
        String baseUrl,
        Integer maxToken
) {
}
