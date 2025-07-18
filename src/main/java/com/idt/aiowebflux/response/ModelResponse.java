package com.idt.aiowebflux.response;

import com.idt.aiowebflux.entity.constant.Feature;

public record ModelResponse(
        String name,
        String provider,
        Feature feature,
        Integer maxToken
) {
}
