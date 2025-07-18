package com.idt.aiowebflux.request;

import com.idt.aiowebflux.dto.SourceMatchDto;

public record DictionaryRequest(
        SourceMatchDto sourceMatchDto
) {
}
